package com.example.recipefinder.data.repository

import com.example.recipefinder.BuildConfig
import com.example.recipefinder.data.database.dao.AiCacheDao
import com.example.recipefinder.data.database.entities.AiCacheEntity
import com.example.recipefinder.data.network.OpenRouterApi
import com.example.recipefinder.data.network.model.AiMessage
import com.example.recipefinder.data.network.model.AiRequest
import com.example.recipefinder.domain.repository.AiRepository
import javax.inject.Inject

class AiRepositoryImpl @Inject constructor(
    private val api: OpenRouterApi,
    private val cacheDao: AiCacheDao
) : AiRepository {

    override suspend fun getRecipeEvaluation(
        recipeName: String,
        recipeIngredients: List<String>,
        recipeSteps: List<String>,
        userIngredients: List<String>
    ): Int? {
        val cacheKey = "${recipeName}_${userIngredients.sorted().joinToString(",")}"
        
        val cached = cacheDao.getCachedEvaluation(cacheKey)
        if (cached != null) return cached.score

        val prompt = """
Ты система оценки рецептов.

ДАНО:

Ингредиенты пользователя:
${userIngredients.joinToString(", ")}

Ингредиенты рецепта:
${recipeIngredients.joinToString(", ")}

Шаги рецепта:
${recipeSteps.joinToString(" ")}

ЗАДАЧА:
Оцени, насколько пользователь может приготовить этот рецепт.

ПРАВИЛА ОЦЕНКИ:
- 100 = все основные ингредиенты есть
- 70-90 = почти всё есть, возможны замены
- 40-60 = есть примерно половина
- 10-30 = мало совпадений
- 0 = ингредиенты не подходят

Учитывай возможные замены ингредиентов.

ФОРМАТ ОТВЕТА:
- Верни ТОЛЬКО одно число от 0 до 100
- Без текста
- Без объяснений
- Без символов
- Пример ответа: 85

ОТВЕТ:
""".trimIndent()

        return try {
            val response = api.getCompletion(
                token = "Bearer ${BuildConfig.OPENROUTER_API_KEY}",
                request = AiRequest(
                    model = "google/gemma-3n-e4b-it:free",
                    messages = listOf(AiMessage(role = "user", content = prompt))
                )
            )
            val resultText = response.choices.firstOrNull()?.message?.content?.trim() ?: return null
            val score = resultText.filter { it.isDigit() }.toIntOrNull()
            
            if (score != null) {
                cacheDao.insert(AiCacheEntity(cacheKey, score))
            }
            score
        } catch (e: Exception) {
            null
        }
    }
}