package com.example.recipeapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipeapp.database.daos.RecipeDao
import com.example.recipeapp.database.tables.Recipe
import com.example.recipeapp.database.typeConverters.IngredientsTypeConverter
import com.example.recipeapp.database.typeConverters.RecipeStepsTypeConverter


@Database(
    entities = [
        Recipe::class
    ],
    version = 1
)
@TypeConverters(
    RecipeStepsTypeConverter::class,
    IngredientsTypeConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract val recipeDao : RecipeDao

}