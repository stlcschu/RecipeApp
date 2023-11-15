package com.example.recipeapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.recipeapp.composables.recipes.RecipeView
import com.example.recipeapp.database.AppDatabase
import com.example.recipeapp.database.events.RecipeEvent
import com.example.recipeapp.database.viewModels.RecipeMenuViewModel
import com.example.recipeapp.database.viewModels.RecipeViewModel
import com.example.recipeapp.enums.RecipeActivityCallReason

class RecipeActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "database.db"
        ).build()
    }

    private val recipesViewModel by viewModels<RecipeViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return RecipeViewModel(database.recipeDao) as T
                }
            }
        }
    )


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recipeId = intent.extras!!.getInt(RECIPE_ID_KEY_NAME)
        recipesViewModel.onEvent(RecipeEvent.SetRecipeId(recipeId))
        val callReason = intent.extras!!.getString(RECIPE_CALL_REASON_KEY_NAME)
            ?.let { RecipeActivityCallReason.fromString(it) }
        setContent {
            val state by recipesViewModel.state.collectAsState()
            when(callReason) {
                RecipeActivityCallReason.NEW -> TODO()
                RecipeActivityCallReason.EDIT -> TODO()
                else -> {
                    RecipeView(state = state, onEvent = recipesViewModel::onEvent)
                }
            }

        }
    }

    companion object {
        const val RECIPE_ID_KEY_NAME = "RECIPE_ID"
        const val RECIPE_CALL_REASON_KEY_NAME = "RECIPE_CALL_REASON"
    }

}