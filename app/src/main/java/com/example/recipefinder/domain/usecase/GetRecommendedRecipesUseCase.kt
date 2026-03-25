package com.example.recipefinder.domain.usecase

import com.example.recipefinder.domain.model.Recipe
import com.example.recipefinder.domain.model.RecipeSearchResult
import com.example.recipefinder.domain.repository.IngredientRepository
import com.example.recipefinder.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRecommendedRecipesUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val ingredientRepository: IngredientRepository
) {
    fun execute(userIngredients: List<Int>): Flow<List<RecipeSearchResult>> {
        return recipeRepository.getAllRecipes().map { recipes ->
            val allSubstitutions = ingredientRepository.getAllSubstitutions()
            val results = mutableListOf<RecipeSearchResult>()
            
            for (recipe in recipes) {
                var matches = 0
                for (recipeIngredientId in recipe.ingredients) {
                    if (recipeIngredientId in userIngredients) {
                        matches++
                    } else {
                        val substitutes = allSubstitutions[recipeIngredientId] ?: emptyList()
                        if (substitutes.any { it in userIngredients }) {
                            matches++
                        }
                    }
                }

                val score = if (recipe.ingredients.isNotEmpty()) {
                    matches.toFloat() / recipe.ingredients.size
                } else 0f
                
                if (score > 0) {
                    results.add(RecipeSearchResult(recipe, score))
                }
            }
            
            results.sortedByDescending { it.matchScore }
        }
    }
}