package com.example.recipeapp.database.typeConverters

import androidx.room.TypeConverter
import com.example.recipeapp.enums.RecipeTags

class RecipeTagsTypeConverter {

    @TypeConverter
    fun recipeTagsToString(recipeTags: List<RecipeTags>) : String {
        return recipeTags.joinToString()
    }

    @TypeConverter
    fun stringToRecipeTags(string: String) : List<RecipeTags> {
        val recipeTags = mutableListOf<RecipeTags>()
        if (string.isBlank()) return emptyList()
        val stringRecipeTags = string.split(", ")
        for (stringRecipeTag in stringRecipeTags) {
            recipeTags.add(RecipeTags.fromString(stringRecipeTag))
        }
        return recipeTags
    }

}