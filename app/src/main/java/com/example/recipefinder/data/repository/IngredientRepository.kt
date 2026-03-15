package com.example.recipefinder.data.repository

import com.example.recipefinder.data.datasource.JsonDataSource
import com.example.recipefinder.domain.model.Ingredient

class IngredientRepository(
    private val dataSource: JsonDataSource
) {

    fun getIngredients(): List<Ingredient> {
        return dataSource.loadIngredients()
    }

    fun getIngredientById(id: Int): Ingredient? {
        return dataSource.getIngredientById(id)
    }

}