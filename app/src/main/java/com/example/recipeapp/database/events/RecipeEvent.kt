package com.example.recipeapp.database.events

import com.example.recipeapp.database.tables.Recipe
import com.example.recipeapp.dataclasses.Ingredients
import com.example.recipeapp.dataclasses.RecipeSteps

sealed interface RecipeEvent {

    object SaveRecipe : RecipeEvent

    data class SetRecipeName(val recipeName: String) : RecipeEvent
    data class SetRecipeIngredients(val recipeIngredients: Ingredients) : RecipeEvent
    data class SetRecipeSteps(val recipeSteps: RecipeSteps) : RecipeEvent

    data class DeleteRecipe(val recipe: Recipe) : RecipeEvent

}