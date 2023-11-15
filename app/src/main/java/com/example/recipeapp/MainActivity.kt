package com.example.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.recipeapp.composables.main.MainView
import com.example.recipeapp.database.AppDatabase
import com.example.recipeapp.database.viewModels.RecipeMenuViewModel

class MainActivity : ComponentActivity() {

    //TODO: Check if the plural form of an ingredient is used if no measurement is used an the value is above 1
    //TODO: Settings
    //TODO: Editing
    //TODO: New recipe creation

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "database.db"
        ).build()
    }

    private val recipesViewModel by viewModels<RecipeMenuViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return RecipeMenuViewModel(database.recipeDao) as T
                }
            }
        }
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state by recipesViewModel.state.collectAsState()
            MainView(state = state, onEvent = recipesViewModel::onEvent, context = this@MainActivity)
        }
    }

}