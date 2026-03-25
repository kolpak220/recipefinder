package com.example.recipefinder.data.database.entities

import androidx.room.Entity

@Entity(
    tableName = "recipe_ingredient_cross_ref",
    primaryKeys = ["recipeId", "ingredientId"]
)
data class RecipeIngredientCrossRef(
    val recipeId: Int,
    val ingredientId: Int
)