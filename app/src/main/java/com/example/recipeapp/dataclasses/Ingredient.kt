package com.example.recipeapp.dataclasses

import com.example.recipeapp.enums.MeasuringUnit

data class Ingredient (
    var name : String,
    var amount : Int,
    var measuringUnit : MeasuringUnit,
    val calories : Float = 0F
)