package com.example.recipeapp.database.typeConverters

import androidx.room.TypeConverter
import com.example.recipeapp.dataclasses.RecipeSteps
import com.google.gson.Gson

class RecipeStepsTypeConverter {

    private val _gson = Gson()

    @TypeConverter
    fun recipeStepsToString(recipeSteps: RecipeSteps) : String {
        return _gson.toJson(recipeSteps)
    }

    @TypeConverter
    fun stringToRecipeSteps(string: String) : RecipeSteps {
        return _gson.fromJson(string, RecipeSteps::class.java)
    }

}