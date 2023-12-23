package com.example.recipeapp.dataclasses

import com.example.recipeapp.enums.MeasuringUnit

data class EditingRecipe(
    val recipeName: String,
    val ingredientNames: List<String>,
    val ingredientAmounts: List<Int>,
    val ingredientMeasurements: List<MeasuringUnit>,
    val cookingSteps: List<String>,
    val measurementUnitDropDown: List<Boolean>
)
