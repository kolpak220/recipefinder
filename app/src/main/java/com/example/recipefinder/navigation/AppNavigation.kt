package com.example.recipefinder.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.recipefinder.presentation.screens.MainScreen
import com.example.recipefinder.presentation.screens.RecipeDetailScreen
import com.example.recipefinder.presentation.screens.RecipesScreen
import com.example.recipefinder.presentation.viewmodel.MainViewModel

@Composable
fun AppNavigation(viewModel: MainViewModel) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {

        composable("main") {
            MainScreen(navController, viewModel)
        }

        composable("recipes") {
            RecipesScreen(navController, viewModel)
        }

        composable("recipe_detail/{recipeId}") { backStackEntry ->

            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toInt() ?: 0

            RecipeDetailScreen(
                navController = navController,
                viewModel = viewModel,
                recipeId = recipeId
            )
        }

    }
}