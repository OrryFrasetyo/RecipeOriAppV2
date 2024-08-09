package com.orryfrasetyo.recipeoriapp.core.di

import com.orryfrasetyo.recipeoriapp.core.data.RecipeRepository
import com.orryfrasetyo.recipeoriapp.core.domain.repository.IRecipeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(recipeRepository: RecipeRepository): IRecipeRepository
}











