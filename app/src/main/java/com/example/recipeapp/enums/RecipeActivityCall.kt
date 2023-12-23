package com.example.recipeapp.enums

enum class RecipeActivityCall(val stringify: String) {
    FROM_DEFAULT("FROM_DEFAULT"),
    FROM_NEW("FROM_NEW");

    companion object {
        fun fromString(value: String) = RecipeActivityCall.values().firstOrNull() { it.stringify == value } ?: throw IllegalArgumentException("Invalid type requested: $value")
    }
}