package com.example.recipeapp.dataclasses

data class RecipeStep(
    var description : String = "",
    var stepTimer : List<StepTimer> = emptyList()
)
