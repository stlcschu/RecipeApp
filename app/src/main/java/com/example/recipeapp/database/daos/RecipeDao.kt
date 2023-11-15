package com.example.recipeapp.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.recipeapp.database.tables.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Transaction
    @Query("SELECT * FROM Recipe")
    fun getAllRecipes() : Flow<List<Recipe>>

    @Transaction
    @Query("SELECT * FROM Recipe WHERE id = :id")
    fun getSingleRecipesById(id: Int) : Flow<Recipe>

    @Transaction
    @Query("SELECT * FROM Recipe ORDER BY dateTimeAdded LIMIT 1")
    fun getLatestAddedRecipe() : Flow<Recipe>

    @Upsert
    suspend fun upsertRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

}