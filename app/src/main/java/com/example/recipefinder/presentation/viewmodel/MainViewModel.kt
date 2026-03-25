package com.example.recipefinder.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.domain.model.Ingredient
import com.example.recipefinder.domain.model.Recipe
import com.example.recipefinder.domain.model.RecipeSearchResult
import com.example.recipefinder.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchIngredientsUseCase: SearchIngredientsUseCase,
    private val getRecommendedRecipesUseCase: GetRecommendedRecipesUseCase,
    private val getIngredientByIdUseCase: GetIngredientByIdUseCase,
    private val getSubstitutesUseCase: GetSubstitutesUseCase,
    private val getRecipeAiEvaluationUseCase: GetRecipeAiEvaluationUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Ingredient>>(emptyList())
    val searchResults: StateFlow<List<Ingredient>> = _searchResults.asStateFlow()

    private val _basket = MutableStateFlow<List<Ingredient>>(emptyList())
    val basket: StateFlow<List<Ingredient>> = _basket.asStateFlow()

    private val _recommendedRecipes = MutableStateFlow<List<RecipeSearchResult>>(emptyList())
    val recommendedRecipes: StateFlow<List<RecipeSearchResult>> = _recommendedRecipes.asStateFlow()

    private val _aiEvaluations = MutableStateFlow<Map<Int, Int?>>(emptyMap())
    val aiEvaluations: StateFlow<Map<Int, Int?>> = _aiEvaluations.asStateFlow()

    private var searchJob: Job? = null
    fun searchIngredients(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            searchIngredientsUseCase.execute(query).collect {
                _searchResults.value = it
            }
        }
    }

    suspend fun getIngredientNameById(id: Int): String {
        return getIngredientByIdUseCase.execute(id)?.name ?: "Неизвестный ингредиент"
    }

    suspend fun getSubstituteInBasket(baseId: Int): String? {
        val substitutes = getSubstitutesUseCase.execute(baseId)
        val inBasket = _basket.value.find { it.id in substitutes }
        return inBasket?.name
    }

    fun addIngredient(ingredient: Ingredient) {
        if (!_basket.value.contains(ingredient)) {
            _basket.value = _basket.value + ingredient
        }
        _searchQuery.value = ""
        _searchResults.value = emptyList()
        findRecipes()
    }

    fun clearBasket() {
        _basket.value = emptyList()
        _aiEvaluations.value = emptyMap()
        findRecipes()
    }

    fun removeIngredient(ingredient: Ingredient) {
        _basket.value = _basket.value - ingredient
        findRecipes()
    }

    private var recipesJob: Job? = null
    fun findRecipes() {
        recipesJob?.cancel()
        recipesJob = viewModelScope.launch {
            val ids = _basket.value.map { it.id }
            getRecommendedRecipesUseCase.execute(ids).collect { results ->
                _recommendedRecipes.value = results
                evaluateTopRecipes(results)
            }
        }
    }

    private fun evaluateTopRecipes(results: List<RecipeSearchResult>) {
        val top1 = results.take(3)
        val currentBasketNames = _basket.value.map { it.name }
        
        val newEvaluations = _aiEvaluations.value.toMutableMap()
        
        top1.forEach { result ->
            if (!newEvaluations.containsKey(result.recipe.id)) {
                newEvaluations[result.recipe.id] = null
                viewModelScope.launch {
                    val recipeIngredientsNames = result.recipe.ingredients.map { getIngredientNameById(it) }
                    
                    val score = getRecipeAiEvaluationUseCase.execute(
                        recipeName = result.recipe.name,
                        recipeIngredients = recipeIngredientsNames,
                        recipeSteps = result.recipe.steps,
                        userIngredients = currentBasketNames
                    )

                    val updatedEvaluations = _aiEvaluations.value.toMutableMap()
                    if (score != null) {
                        updatedEvaluations[result.recipe.id] = score
                    } else {
                        updatedEvaluations.remove(result.recipe.id)
                    }
                    _aiEvaluations.value = updatedEvaluations
                }
            }
        }
        _aiEvaluations.value = newEvaluations
    }
}