package com.example.recipeapp.dataclasses

data class RecipeSteps (
    val steps : List<String> = emptyList(),
    val amountOfSteps : Int = 0,
    val timeNeededInMinutes : Int = 0
)