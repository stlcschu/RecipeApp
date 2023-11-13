package com.example.recipeapp.database.typeConverters

import androidx.room.TypeConverter
import com.example.recipeapp.dataclasses.Ingredients
import com.google.gson.Gson

class IngredientsTypeConverter {

    private val _gson = Gson()

    @TypeConverter
    fun ingredientsToString(ingredients: Ingredients) : String {
        return _gson.toJson(ingredients)
    }

    @TypeConverter
    fun stringToIngredients(string: String) : Ingredients {
        return _gson.fromJson(string, Ingredients::class.java)
    }

}