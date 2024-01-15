package com.example.recipeapp.dataclasses

import com.example.recipeapp.enums.MeasuringUnit

data class Ingredient (
    var name : String = "",
    var amount : Int = 0,
    var measuringUnit : MeasuringUnit = MeasuringUnit.NONE,
    val calories : Float = 0F,
    val optional : Boolean = false
)