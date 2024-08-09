package com.orryfrasetyo.recipeoriapp.core.data

import com.orryfrasetyo.recipeoriapp.core.data.source.local.LocalDataSource
import com.orryfrasetyo.recipeoriapp.core.data.source.remote.RemoteDataSource
import com.orryfrasetyo.recipeoriapp.core.data.source.remote.network.ApiResponse
import com.orryfrasetyo.recipeoriapp.core.data.source.remote.response.RecipeResponse
import com.orryfrasetyo.recipeoriapp.core.domain.model.Recipe
import com.orryfrasetyo.recipeoriapp.core.domain.repository.IRecipeRepository
import com.orryfrasetyo.recipeoriapp.core.utils.AppExecutors
import com.orryfrasetyo.recipeoriapp.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IRecipeRepository{
    override fun getRecipes(): Flow<Resource<List<Recipe>>> =
        object : NetworkBoundResource<List<Recipe>, List<RecipeResponse>>() {
            override fun loadFromDB(): Flow<List<Recipe>> {
                return localDataSource.getRecipes().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<RecipeResponse>>> =
                remoteDataSource.getRecipes()

            override suspend fun saveCallResult(data: List<RecipeResponse>) {
                val recipeList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertRecipe(recipeList)
            }

            override fun shouldFetch(data: List<Recipe>?): Boolean =
                data == null || data.isEmpty()

        }.asFlow()

    override fun getDetailRecipe(id: Int): Flow<Resource<Recipe>> {
        return object : NetworkOnlyResource<Recipe, RecipeResponse>() {
            override fun loadFromNetwork(data: RecipeResponse): Flow<Recipe> {
                return DataMapper.mapResponseToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<RecipeResponse>> {
                return remoteDataSource.getDetailRecipe(id)
            }

        }.asFlow()
    }

    override fun getRecipeById(id: Int): Flow<Recipe> {
        return localDataSource.getRecipeById(id).map {
            DataMapper.mapEntityToDomain(it)
        }
    }

    override fun getFavoriteRecipe(): Flow<List<Recipe>> {
        return localDataSource.getFavoriteRecipe().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteRecipe(recipe: Recipe, state: Boolean) {
        val recipeEntity = DataMapper.mapDomainToEntity(recipe)
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteRecipe(recipeEntity, state)
        }
    }

}











