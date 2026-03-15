package com.example.recipefinder.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.recipefinder.domain.model.Ingredient
import com.example.recipefinder.domain.model.Recipe
import com.example.recipefinder.domain.usecase.GetIngredientByIdUseCase
import com.example.recipefinder.domain.usecase.GetRecommendedRecipesUseCase
import com.example.recipefinder.domain.usecase.SearchIngredientsUseCase

class MainViewModel(
    private val searchIngredientsUseCase: SearchIngredientsUseCase,
    private val getRecommendedRecipesUseCase: GetRecommendedRecipesUseCase,
    private val getIngredientByIdUseCase: GetIngredientByIdUseCase
) : ViewModel() {

    var searchQuery by mutableStateOf("")

    var searchResults by mutableStateOf<List<Ingredient>>(emptyList())

    var basket by mutableStateOf<List<Ingredient>>(emptyList())

    var recommendedRecipes by mutableStateOf<List<Recipe>>(emptyList())

    fun searchIngredients(query: String) {

        searchQuery = query
        searchResults = searchIngredientsUseCase.execute(query)

    }
    fun getIngredientNameById(id: Int): String {
        return getIngredientByIdUseCase.execute(id)?.name ?: "Неизвестный ингредиент"
    }
    fun addIngredient(ingredient: Ingredient) {

        if (!basket.contains(ingredient)) {
            basket = basket + ingredient
        }

        searchQuery = ""
        searchResults = emptyList()

        findRecipes()
    }

    fun clearBasket() {
        basket = emptyList()
        findRecipes()
    }
    fun removeIngredient(ingredient: Ingredient) {

        basket = basket - ingredient

        findRecipes()

    }

    fun findRecipes() {

        val ids = basket.map { it.id }

        recommendedRecipes =
            getRecommendedRecipesUseCase.execute(ids)

    }

}