package com.orryfrasetyo.recipeoriapp.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.orryfrasetyo.recipeoriapp.core.domain.usecase.RecipeUseCase

class FavoriteViewModel(private val recipeUseCase: RecipeUseCase) : ViewModel() {

    fun getFavoriteRecipe() = recipeUseCase.getFavoriteRecipe().asLiveData()
}