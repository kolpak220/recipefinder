package com.example.recipefinder.domain.model

enum class Difficulty {
    EASY, MEDIUM, HARD
}
data class Recipe(
    val id: Int,
    val name: String,
    val ingredients: List<Int>,
    val steps: List<String>,
    val minutes: Int,
    val difficulty: Difficulty
)