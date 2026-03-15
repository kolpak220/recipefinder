package com.example.recipefinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.recipefinder.data.datasource.JsonDataSource
import com.example.recipefinder.data.repository.IngredientRepository
import com.example.recipefinder.data.repository.RecipeRepository
import com.example.recipefinder.domain.theme.MyApplicationTheme
import com.example.recipefinder.domain.usecase.GetIngredientByIdUseCase
import com.example.recipefinder.domain.usecase.GetRecommendedRecipesUseCase
import com.example.recipefinder.domain.usecase.SearchIngredientsUseCase
import com.example.recipefinder.navigation.AppNavigation
import com.example.recipefinder.presentation.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataSource = JsonDataSource(this)

        val ingredientRepository = IngredientRepository(dataSource)
        val recipeRepository = RecipeRepository(dataSource)

        val searchIngredientsUseCase =
            SearchIngredientsUseCase(ingredientRepository)

        val getRecommendedRecipesUseCase =
            GetRecommendedRecipesUseCase(recipeRepository)

        val getIngredientByIdUseCase = GetIngredientByIdUseCase(ingredientRepository)

        val viewModel = MainViewModel(
            searchIngredientsUseCase,
            getRecommendedRecipesUseCase,
            getIngredientByIdUseCase
        )

        setContent {
            MyApplicationTheme {
                AppNavigation(viewModel)
            }

        }
    }
}