package com.example.recipefinder.data.repository

import com.example.recipefinder.data.datasource.JsonDataSource
import com.example.recipefinder.domain.model.Recipe

class RecipeRepository(
    private val dataSource: JsonDataSource
) {

    fun getRecipes(): List<Recipe> {
        return dataSource.loadRecipes()
    }

}