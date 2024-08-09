package com.orryfrasetyo.recipeoriapp.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
data class RecipeEntity(
    @PrimaryKey
    @ColumnInfo(name = "recipeId")
    val recipeId: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "image")
    val image: String,

    @ColumnInfo(name = "healthScore")
    val healthScore: Int,

    @ColumnInfo(name = "dairyFree")
    val dairyFree: Boolean,

    @ColumnInfo(name = "glutenFree")
    val glutenFree: Boolean,

    @ColumnInfo(name = "aggregateLikes")
    val aggregateLikes: Int,

    @ColumnInfo(name = "readyInMinutes")
    val readyInMinutes: Int,

    @ColumnInfo(name = "vegetarian")
    val vegetarian: Boolean,

    @ColumnInfo(name = "summary")
    val summary: String,

    @ColumnInfo(name = "veryHealthy")
    val veryHealthy: Boolean,

    @ColumnInfo(name = "vegan")
    val vegan: Boolean,

    @ColumnInfo(name = "cheap")
    val cheap: Boolean,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)