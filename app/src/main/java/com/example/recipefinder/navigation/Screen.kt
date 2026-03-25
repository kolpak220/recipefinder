package com.example.recipefinder.navigation

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Recipes : Screen("recipes")
    object RecipeDetail : Screen("recipe_detail/{recipeId}") {
        fun createRoute(recipeId: Int) = "recipe_detail/$recipeId"
    }
}