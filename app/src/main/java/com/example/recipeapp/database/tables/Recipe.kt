package com.example.recipeapp.database.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipeapp.dataclasses.Ingredients
import com.example.recipeapp.dataclasses.RecipeSteps

@Entity
data class Recipe(
    val name : String,
    val ingredients: Ingredients,
    val steps : RecipeSteps,
    @PrimaryKey(autoGenerate = true) val id : Int = 0
)
