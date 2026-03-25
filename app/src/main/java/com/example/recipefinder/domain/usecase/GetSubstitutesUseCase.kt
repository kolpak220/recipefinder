package com.example.recipefinder.domain.usecase

import com.example.recipefinder.domain.repository.IngredientRepository
import javax.inject.Inject

class GetSubstitutesUseCase @Inject constructor(
    private val repository: IngredientRepository
) {
    suspend fun execute(ingredientId: Int): List<Int> {
        return repository.getSubstitutesForIngredient(ingredientId)
    }
}