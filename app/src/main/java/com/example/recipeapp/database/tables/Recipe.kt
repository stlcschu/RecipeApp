package com.example.recipeapp.database.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipeapp.dataclasses.Ingredients
import com.example.recipeapp.dataclasses.RecipeSteps
import com.example.recipeapp.enums.RecipeTags
import java.time.LocalDateTime

@Entity
data class Recipe(
    val name : String = "",
    val mainDish : String = "",
    val sideDish : String = "",
    val prelude : String = "",
    val image : String = "",
    val ingredients: Ingredients = Ingredients(),
    val steps : RecipeSteps = RecipeSteps(),
    val recipeTags : List<RecipeTags> = listOf<RecipeTags>(),
    val dateTimeAdded : LocalDateTime = LocalDateTime.now(),
    @PrimaryKey(autoGenerate = true) val id : Int = 0
)
