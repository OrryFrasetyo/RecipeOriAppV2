package com.orryfrasetyo.recipeoriapp.favorite.di

import android.content.Context
import com.orryfrasetyo.recipeoriapp.di.FavoriteModuleDependencies
import com.orryfrasetyo.recipeoriapp.favorite.FavoriteFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    dependencies = [FavoriteModuleDependencies::class]
)

interface FavoriteComponent {

    fun inject(favoriteFragment: FavoriteFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }
}








