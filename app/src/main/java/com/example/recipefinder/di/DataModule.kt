package com.example.recipefinder.di

import android.content.Context
import androidx.room.Room
import com.example.recipefinder.data.database.AppDatabase
import com.example.recipefinder.data.database.dao.AiCacheDao
import com.example.recipefinder.data.database.dao.IngredientDao
import com.example.recipefinder.data.database.dao.RecipeDao
import com.example.recipefinder.data.database.dao.SubstitutionDao
import com.example.recipefinder.data.datasource.JsonDataSource
import com.example.recipefinder.data.repository.AiRepositoryImpl
import com.example.recipefinder.data.repository.IngredientRepositoryImpl
import com.example.recipefinder.data.repository.RecipeRepositoryImpl
import com.example.recipefinder.domain.repository.AiRepository
import com.example.recipefinder.domain.repository.IngredientRepository
import com.example.recipefinder.domain.repository.RecipeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindIngredientRepository(impl: IngredientRepositoryImpl): IngredientRepository

    @Binds
    @Singleton
    abstract fun bindRecipeRepository(impl: RecipeRepositoryImpl): RecipeRepository

    @Binds
    @Singleton
    abstract fun bindAiRepository(impl: AiRepositoryImpl): AiRepository

    companion object {
        @Provides
        @Singleton
        fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "recipe_finder_db"
            )
            .fallbackToDestructiveMigration()
            .build()
        }

        @Provides
        fun provideIngredientDao(db: AppDatabase): IngredientDao = db.ingredientDao()

        @Provides
        fun provideRecipeDao(db: AppDatabase): RecipeDao = db.recipeDao()

        @Provides
        fun provideSubstitutionDao(db: AppDatabase): SubstitutionDao = db.substitutionDao()

        @Provides
        fun provideAiCacheDao(db: AppDatabase): AiCacheDao = db.aiCacheDao()

        @Provides
        @Singleton
        fun provideJsonDataSource(@ApplicationContext context: Context): JsonDataSource {
            return JsonDataSource(context)
        }
    }
}