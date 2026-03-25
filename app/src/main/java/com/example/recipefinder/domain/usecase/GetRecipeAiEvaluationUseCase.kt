package com.example.recipefinder.domain.usecase

import com.example.recipefinder.domain.repository.AiRepository
import javax.inject.Inject

class GetRecipeAiEvaluationUseCase @Inject constructor(
    private val repository: AiRepository
) {
    suspend fun execute(
        recipeName: String,
        recipeIngredients: List<String>,
        recipeSteps: List<String>,
        userIngredients: List<String>
    ): Int? {
        return repository.getRecipeEvaluation(recipeName, recipeIngredients, recipeSteps, userIngredients)
    }
}