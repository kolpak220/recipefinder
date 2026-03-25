package com.example.recipefinder.di

import com.example.recipefinder.domain.repository.IngredientRepository
import com.example.recipefinder.domain.repository.RecipeRepository
import com.example.recipefinder.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    @ViewModelScoped
    fun provideSearchIngredientsUseCase(repository: IngredientRepository): SearchIngredientsUseCase {
        return SearchIngredientsUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetRecommendedRecipesUseCase(
        recipeRepository: RecipeRepository,
        ingredientRepository: IngredientRepository
    ): GetRecommendedRecipesUseCase {
        return GetRecommendedRecipesUseCase(recipeRepository, ingredientRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetIngredientByIdUseCase(repository: IngredientRepository): GetIngredientByIdUseCase {
        return GetIngredientByIdUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetSubstitutesUseCase(repository: IngredientRepository): GetSubstitutesUseCase {
        return GetSubstitutesUseCase(repository)
    }
}