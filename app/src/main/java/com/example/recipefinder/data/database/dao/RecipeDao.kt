package com.example.recipefinder.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.recipefinder.data.database.entities.RecipeEntity
import com.example.recipefinder.data.database.entities.RecipeIngredientCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT ingredientId FROM recipe_ingredient_cross_ref WHERE recipeId = :recipeId")
    suspend fun getIngredientIdsForRecipe(recipeId: Int): List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipes: List<RecipeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossRefs(crossRefs: List<RecipeIngredientCrossRef>)
}