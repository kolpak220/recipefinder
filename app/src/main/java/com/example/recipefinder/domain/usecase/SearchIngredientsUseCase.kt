package com.example.recipefinder.domain.usecase

import com.example.recipefinder.data.repository.IngredientRepository
import com.example.recipefinder.domain.model.Ingredient

class SearchIngredientsUseCase(
    private val repository: IngredientRepository
) {

    fun execute(query: String): List<Ingredient> {

        val ingredients = repository.getIngredients()

        if (query.isBlank()) return emptyList()

        return ingredients.filter {
            it.name.contains(query, ignoreCase = true)
        }

    }
}