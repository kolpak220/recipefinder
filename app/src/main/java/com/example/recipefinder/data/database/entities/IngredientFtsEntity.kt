package com.example.recipefinder.data.database.entities

import androidx.room.Entity
import androidx.room.Fts4

@Fts4(contentEntity = IngredientEntity::class)
@Entity(tableName = "ingredients_fts")
data class IngredientFtsEntity(
    val name: String
)