package com.example.recipefinder.domain.model

data class RecipeSearchResult(
    val recipe: Recipe,
    val matchScore: Float
)