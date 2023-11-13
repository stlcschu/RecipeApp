package com.example.recipeapp.states

import com.example.recipeapp.database.tables.Recipe
import com.example.recipeapp.dataclasses.Ingredients
import com.example.recipeapp.dataclasses.RecipeSteps

data class RecipeState (
    val recipes : List<Recipe> = emptyList(),
    val recipeName : String = "",
    val recipeIngredients: Ingredients = Ingredients(),
    val recipeSteps: RecipeSteps = RecipeSteps()
)