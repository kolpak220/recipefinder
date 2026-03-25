package com.example.recipefinder.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipefinder.domain.model.Difficulty
import com.example.recipefinder.domain.model.Recipe

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val steps: List<String>,
    val minutes: Int,
    val difficulty: Difficulty
)

fun RecipeEntity.toDomain(ingredientIds: List<Int>) = Recipe(
    id = id,
    name = name,
    ingredients = ingredientIds,
    steps = steps,
    minutes = minutes,
    difficulty = difficulty
)

fun Recipe.toEntity() = RecipeEntity(
    id = id,
    name = name,
    steps = steps,
    minutes = minutes,
    difficulty = difficulty
)