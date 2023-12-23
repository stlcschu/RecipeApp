package com.example.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.recipeapp.composables.recipes.NewRecipeView
import com.example.recipeapp.database.AppDatabase
import com.example.recipeapp.database.viewModels.NewRecipeViewModel

class NewRecipeActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "database.db"
        ).build()
    }

    private val recipesViewModel by viewModels<NewRecipeViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NewRecipeViewModel(database.recipeDao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewRecipeView(onEvent = recipesViewModel::onEvent, context = this@NewRecipeActivity)
        }
    }

}