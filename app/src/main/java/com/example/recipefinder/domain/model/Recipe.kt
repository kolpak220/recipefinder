package com.example.recipefinder.domain.model

data class Recipe(
    val id: Int,
    val name: String,
    val ingredients: List<Int>,
    val steps: List<String>
)