package com.example.recipefinder.data.datasource

import android.content.Context
import com.example.recipefinder.domain.model.Ingredient
import com.example.recipefinder.domain.model.Recipe
import org.json.JSONArray

class JsonDataSource(private val context: Context) {

    fun loadIngredients(): List<Ingredient> {

        val json = context.assets
            .open("ingredients.json")
            .bufferedReader()
            .use { it.readText() }

        val array = JSONArray(json)

        val ingredients = mutableListOf<Ingredient>()

        for (i in 0 until array.length()) {

            val obj = array.getJSONObject(i)

            ingredients.add(
                Ingredient(
                    id = obj.getInt("id"),
                    name = obj.getString("name")
                )
            )
        }

        return ingredients
    }
    fun getIngredientById(id: Int): Ingredient? {
        return loadIngredients().find { it.id == id }
    }

    fun loadRecipes(): List<Recipe> {

        val json = context.assets
            .open("recipes.json")
            .bufferedReader()
            .use { it.readText() }

        val array = JSONArray(json)

        val recipes = mutableListOf<Recipe>()

        for (i in 0 until array.length()) {

            val obj = array.getJSONObject(i)

            val ingredientsArray = obj.getJSONArray("ingredients")

            val ingredients = mutableListOf<Int>()

            for (j in 0 until ingredientsArray.length()) {
                ingredients.add(ingredientsArray.getInt(j))
            }

            val stepsArray = obj.getJSONArray("steps")

            val steps = mutableListOf<String>()

            for (j in 0 until stepsArray.length()) {
                steps.add(stepsArray.getString(j))
            }

            recipes.add(
                Recipe(
                    id = obj.getInt("id"),
                    name = obj.getString("name"),
                    ingredients = ingredients,
                    steps = steps
                )
            )
        }

        return recipes
    }
}