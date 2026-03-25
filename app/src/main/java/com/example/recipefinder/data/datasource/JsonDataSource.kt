package com.example.recipefinder.data.datasource

import android.content.Context
import com.example.recipefinder.domain.model.Difficulty
import com.example.recipefinder.domain.model.Ingredient
import com.example.recipefinder.domain.model.Recipe
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable
data class SubstitutionJson(
    val base_id: Int,
    val substitutes: List<Int>
)

class JsonDataSource(private val context: Context) {

    private val json = Json { ignoreUnknownKeys = true }

    fun loadIngredients(): List<Ingredient> {
        val jsonString = context.assets.open("ingredients.json").bufferedReader().use { it.readText() }
        val array = Json.parseToJsonElement(jsonString).jsonArray
        return array.map {
            Ingredient(
                id = it.jsonObject["id"]!!.jsonPrimitive.content.toInt(),
                name = it.jsonObject["name"]!!.jsonPrimitive.content
            )
        }
    }

    fun getIngredientById(id: Int): Ingredient? {
        return loadIngredients().find { it.id == id }
    }

    fun loadRecipes(): List<Recipe> {
        val jsonString = context.assets.open("recipes.json").bufferedReader().use { it.readText() }
        val array = Json.parseToJsonElement(jsonString).jsonArray
        return array.map {
            val obj = it.jsonObject
            val difficultyStr = obj["difficulty"]!!.jsonPrimitive.content.lowercase()
            val difficulty = when (difficultyStr) {
                "easy" -> Difficulty.EASY
                "medium" -> Difficulty.MEDIUM
                "hard" -> Difficulty.HARD
                else -> Difficulty.EASY
            }
            Recipe(
                id = obj["id"]!!.jsonPrimitive.content.toInt(),
                name = obj["name"]!!.jsonPrimitive.content,
                ingredients = obj["ingredients"]!!.jsonArray.map { it.jsonPrimitive.content.toInt() },
                steps = obj["steps"]!!.jsonArray.map { it.jsonPrimitive.content },
                minutes = obj["minutes"]!!.jsonPrimitive.content.toInt(),
                difficulty = difficulty
            )
        }
    }

    fun loadSubstitutions(): List<SubstitutionJson> {
        val jsonString = context.assets.open("ingredient_mappings.json").bufferedReader().use { it.readText() }
        return json.decodeFromString(jsonString)
    }
}