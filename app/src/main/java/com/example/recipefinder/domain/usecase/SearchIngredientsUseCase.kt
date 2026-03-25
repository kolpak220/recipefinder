package com.example.recipefinder.domain.usecase

import com.example.recipefinder.domain.model.Ingredient
import com.example.recipefinder.domain.repository.IngredientRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchIngredientsUseCase @Inject constructor(
    private val repository: IngredientRepository
) {
    fun execute(query: String): Flow<List<Ingredient>> {
        return repository.searchIngredients(query)
    }
}