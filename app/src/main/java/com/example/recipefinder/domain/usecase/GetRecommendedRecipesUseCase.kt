package com.example.recipefinder.domain.usecase

import com.example.recipefinder.data.repository.RecipeRepository
import com.example.recipefinder.domain.model.Recipe

class GetRecommendedRecipesUseCase(
    private val repository: RecipeRepository
) {

    fun execute(userIngredients: List<Int>): List<Recipe> {

        val recipes = repository.getRecipes()

        return recipes
            .map { recipe ->

                val matches = recipe.ingredients.count { it in userIngredients }

                val score = matches.toFloat() / recipe.ingredients.size

                Pair(recipe, score)

            }
            .filter { it.second > 0 }
            .sortedByDescending { it.second }
            .map { it.first }

    }
}