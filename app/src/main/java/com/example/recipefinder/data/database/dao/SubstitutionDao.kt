package com.example.recipefinder.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipefinder.data.database.entities.IngredientSubstitutionEntity

@Dao
interface SubstitutionDao {
    @Query("SELECT substituteId FROM ingredient_substitutions WHERE baseId = :baseId")
    suspend fun getSubstitutesForIngredient(baseId: Int): List<Int>

    @Query("SELECT * FROM ingredient_substitutions")
    suspend fun getAllSubstitutions(): List<IngredientSubstitutionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(substitutions: List<IngredientSubstitutionEntity>)
}