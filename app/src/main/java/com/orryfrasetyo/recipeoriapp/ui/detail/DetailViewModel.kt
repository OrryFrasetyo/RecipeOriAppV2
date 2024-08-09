package com.orryfrasetyo.recipeoriapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.orryfrasetyo.recipeoriapp.core.domain.model.Recipe
import com.orryfrasetyo.recipeoriapp.core.domain.usecase.RecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val recipeUseCase: RecipeUseCase) : ViewModel() {
    fun getDetailRecipe(id: Int) = recipeUseCase.getDetailRecipe(id).asLiveData()

    fun getRecipeById(id: Int) = recipeUseCase.getRecipeById(id).asLiveData()

    fun setFavoriteRecipe(recipe: Recipe, state: Boolean) =
        recipeUseCase.setFavoriteRecipe(recipe, state)
}