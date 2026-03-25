package com.example.recipefinder.domain.repository

import com.example.recipefinder.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getAllRecipes(): Flow<List<Recipe>>
    suspend fun insertRecipes(recipes: List<Recipe>)
}