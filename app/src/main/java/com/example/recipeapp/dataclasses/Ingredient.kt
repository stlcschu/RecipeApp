package com.example.recipeapp.dataclasses

import com.example.recipeapp.enums.MeasuringUnit

data class Ingredient (
    val name : String,
    val amount : Int,
    val measuringUnit : MeasuringUnit,
    val calories : Float = 0F
)