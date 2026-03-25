package com.example.recipefinder.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.recipefinder.navigation.Screen
import com.example.recipefinder.presentation.viewmodel.MainViewModel

@Composable
fun RecipesScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val recommendedRecipes by viewModel.recommendedRecipes.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Рекомендованные рецепты",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(recommendedRecipes) { result ->
                val recipe = result.recipe
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            navController.navigate(Screen.RecipeDetail.createRoute(recipe.id))
                        }
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = recipe.name,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Ингредиентов: ${recipe.ingredients.size}"
                        )
                        Text(
                            text = "Совпадение: ${(result.matchScore * 100).toInt()}%"
                        )
                    }
                }
            }
        }
    }
}