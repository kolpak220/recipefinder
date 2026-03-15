package com.example.recipefinder.domain.usecase

import com.example.recipefinder.data.repository.IngredientRepository
import com.example.recipefinder.domain.model.Ingredient

class GetIngredientByIdUseCase(private val repository: IngredientRepository) {

    fun execute(id: Int): Ingredient? {
        return repository.getIngredientById(id)
    }
}