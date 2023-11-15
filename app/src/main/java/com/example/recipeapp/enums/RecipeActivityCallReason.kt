package com.example.recipeapp.enums

enum class RecipeActivityCallReason(val stringify: String) {
    DEFAULT("DEFAULT"),
    NEW("NEW"),
    EDIT("EDIT");

    companion object {
        fun fromString(value: String) = RecipeActivityCallReason.values().firstOrNull() { it.stringify == value } ?: throw IllegalArgumentException("Invalid type requested: $value")
    }
}