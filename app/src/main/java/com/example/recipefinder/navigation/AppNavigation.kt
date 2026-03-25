package com.example.recipefinder.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.*
import com.example.recipefinder.presentation.screens.MainScreen
import com.example.recipefinder.presentation.screens.RecipeDetailScreen
import com.example.recipefinder.presentation.screens.RecipesScreen
import com.example.recipefinder.presentation.viewmodel.MainViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: MainViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {
            MainScreen(navController, viewModel)
        }

        composable(Screen.Recipes.route) {
            RecipesScreen(navController, viewModel)
        }

        composable(Screen.RecipeDetail.route) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")?.toInt() ?: 0
            RecipeDetailScreen(
                navController = navController,
                viewModel = viewModel,
                recipeId = recipeId
            )
        }
    }
}