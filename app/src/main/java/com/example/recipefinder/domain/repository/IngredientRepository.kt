package com.example.recipefinder.domain.repository

import com.example.recipefinder.domain.model.Ingredient
import kotlinx.coroutines.flow.Flow

interface IngredientRepository {
    fun getAllIngredients(): Flow<List<Ingredient>>
    fun searchIngredients(query: String): Flow<List<Ingredient>>
    suspend fun getIngredientById(id: Int): Ingredient?
    suspend fun insertIngredients(ingredients: List<Ingredient>)
    suspend fun insertSubstitutions(substitutions: List<Pair<Int, Int>>)
    suspend fun getSubstitutesForIngredient(id: Int): List<Int>
    suspend fun getAllSubstitutions(): Map<Int, List<Int>>
}