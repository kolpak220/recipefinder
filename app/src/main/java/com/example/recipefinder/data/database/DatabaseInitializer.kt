package com.example.recipefinder.data.database

import com.example.recipefinder.data.datasource.JsonDataSource
import com.example.recipefinder.domain.repository.IngredientRepository
import com.example.recipefinder.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseInitializer @Inject constructor(
    private val jsonDataSource: JsonDataSource,
    private val ingredientRepository: IngredientRepository,
    private val recipeRepository: RecipeRepository
) {
    suspend fun initialize() {
        val currentIngredients = ingredientRepository.getAllIngredients().first()
        if (currentIngredients.isEmpty()) {
            val ingredients = jsonDataSource.loadIngredients()
            ingredientRepository.insertIngredients(ingredients)

            val recipes = jsonDataSource.loadRecipes()
            recipeRepository.insertRecipes(recipes)

            val substitutionsJson = jsonDataSource.loadSubstitutions()
            val substitutions = substitutionsJson.flatMap { json ->
                json.substitutes.map { substituteId ->
                    Pair(json.base_id, substituteId)
                }
            }
            ingredientRepository.insertSubstitutions(substitutions)
        }
    }
}