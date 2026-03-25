package com.example.recipefinder.domain.repository

interface AiRepository {
    suspend fun getRecipeEvaluation(
        recipeName: String,
        recipeIngredients: List<String>,
        recipeSteps: List<String>,
        userIngredients: List<String>
    ): Int?
}