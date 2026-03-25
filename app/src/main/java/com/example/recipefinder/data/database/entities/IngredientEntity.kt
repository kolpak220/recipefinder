package com.example.recipefinder.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipefinder.domain.model.Ingredient

@Entity(tableName = "ingredients")
data class IngredientEntity(
    @PrimaryKey val id: Int,
    val name: String
)

fun IngredientEntity.toDomain() = Ingredient(
    id = id,
    name = name
)

fun Ingredient.toEntity() = IngredientEntity(
    id = id,
    name = name
)