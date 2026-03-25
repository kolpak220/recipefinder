package com.example.recipefinder.data.repository

import com.example.recipefinder.data.database.dao.IngredientDao
import com.example.recipefinder.data.database.dao.SubstitutionDao
import com.example.recipefinder.data.database.entities.IngredientSubstitutionEntity
import com.example.recipefinder.data.database.entities.toDomain
import com.example.recipefinder.data.database.entities.toEntity
import com.example.recipefinder.domain.model.Ingredient
import com.example.recipefinder.domain.repository.IngredientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IngredientRepositoryImpl @Inject constructor(
    private val ingredientDao: IngredientDao,
    private val substitutionDao: SubstitutionDao
) : IngredientRepository {

    override fun getAllIngredients(): Flow<List<Ingredient>> {
        return ingredientDao.getAllIngredients().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun searchIngredients(query: String): Flow<List<Ingredient>> {
        return ingredientDao.searchIngredients("*$query*").map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getIngredientById(id: Int): Ingredient? {
        return ingredientDao.getIngredientById(id)?.toDomain()
    }

    override suspend fun insertIngredients(ingredients: List<Ingredient>) {
        ingredientDao.insertAll(ingredients.map { it.toEntity() })
    }

    override suspend fun insertSubstitutions(substitutions: List<Pair<Int, Int>>) {
        val entities = substitutions.map {
            IngredientSubstitutionEntity(it.first, it.second)
        }
        substitutionDao.insertAll(entities)
    }

    override suspend fun getSubstitutesForIngredient(id: Int): List<Int> {
        return substitutionDao.getSubstitutesForIngredient(id)
    }

    override suspend fun getAllSubstitutions(): Map<Int, List<Int>> {
        val all = substitutionDao.getAllSubstitutions()
        return all.groupBy({ it.baseId }, { it.substituteId })
    }
}