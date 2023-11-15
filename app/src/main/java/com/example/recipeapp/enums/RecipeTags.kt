package com.example.recipeapp.enums

enum class RecipeTags(val stringify: String) {
    NONE("None");

    companion object {
        fun fromString(value: String) = RecipeTags.values().firstOrNull() { it.stringify == value } ?: throw IllegalArgumentException("Invalid type requested: $value")
    }
}