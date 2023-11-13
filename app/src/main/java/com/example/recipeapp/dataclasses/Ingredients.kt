package com.example.recipeapp.dataclasses

data class Ingredients (
    val ingredients : List<Ingredient> = emptyList(),
    val amountOfIngredients : Int = 0
)
