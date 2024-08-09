package com.orryfrasetyo.recipeoriapp.core.domain.repository

import com.orryfrasetyo.recipeoriapp.core.data.Resource
import com.orryfrasetyo.recipeoriapp.core.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface IRecipeRepository {

    fun getRecipes(): Flow<Resource<List<Recipe>>>

    fun getDetailRecipe(id: Int): Flow<Resource<Recipe>>

    fun getRecipeById(id: Int): Flow<Recipe>

    fun getFavoriteRecipe(): Flow<List<Recipe>>

    fun setFavoriteRecipe(recipe: Recipe, state: Boolean)
}