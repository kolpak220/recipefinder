package com.example.recipefinder.data.repository

import com.example.recipefinder.data.database.dao.RecipeDao
import com.example.recipefinder.data.database.entities.RecipeIngredientCrossRef
import com.example.recipefinder.data.database.entities.toDomain
import com.example.recipefinder.data.database.entities.toEntity
import com.example.recipefinder.domain.model.Recipe
import com.example.recipefinder.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao
) : RecipeRepository {

    override fun getAllRecipes(): Flow<List<Recipe>> {
        return recipeDao.getAllRecipes().map { entities ->
            entities.map { entity ->
                val ingredientIds = recipeDao.getIngredientIdsForRecipe(entity.id)
                entity.toDomain(ingredientIds)
            }
        }
    }

    override suspend fun insertRecipes(recipes: List<Recipe>) {
        recipeDao.insertAll(recipes.map { it.toEntity() })
        val crossRefs = recipes.flatMap { recipe ->
            recipe.ingredients.map { ingredientId ->
                RecipeIngredientCrossRef(recipe.id, ingredientId)
            }
        }
        recipeDao.insertCrossRefs(crossRefs)
    }
}