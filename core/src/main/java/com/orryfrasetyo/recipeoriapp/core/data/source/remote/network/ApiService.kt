package com.orryfrasetyo.recipeoriapp.core.data.source.remote.network

import com.orryfrasetyo.recipeoriapp.core.BuildConfig
import com.orryfrasetyo.recipeoriapp.core.data.source.remote.response.ListRecipeResponse
import com.orryfrasetyo.recipeoriapp.core.data.source.remote.response.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("complexSearch")
    suspend fun getRecipes(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("addRecipeInformation") addRecipeInformation: String = "true",
        @Query("fillIngredients") fillIngredients: String = "true"
    ): ListRecipeResponse

    @GET("{id}/information")
    suspend fun getDetailRecipe(
        @Path("id") id: Int,
        @Query("apiKey")apiKey: String = BuildConfig.API_KEY
    ): RecipeResponse
}