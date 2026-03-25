package com.example.recipefinder.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipefinder.data.database.converters.Converters
import com.example.recipefinder.data.database.dao.AiCacheDao
import com.example.recipefinder.data.database.dao.IngredientDao
import com.example.recipefinder.data.database.dao.RecipeDao
import com.example.recipefinder.data.database.dao.SubstitutionDao
import com.example.recipefinder.data.database.entities.*

@Database(
    entities = [
        IngredientEntity::class,
        IngredientFtsEntity::class,
        RecipeEntity::class,
        RecipeIngredientCrossRef::class,
        IngredientSubstitutionEntity::class,
        AiCacheEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ingredientDao(): IngredientDao
    abstract fun recipeDao(): RecipeDao
    abstract fun substitutionDao(): SubstitutionDao
    abstract fun aiCacheDao(): AiCacheDao
}