package com.example.recipeapp

import android.os.Build
import android.os.Bundle
import android.util.Log
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
import com.example.recipeapp.database.viewModels.RecipeViewModel
import com.example.recipeapp.enums.RecipeActivityCall

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
        val callReason = intent.extras!!.getString(RECIPE_CALL_REASON_KEY_NAME)
            ?.let { RecipeActivityCall.fromString(it) }
        recipesViewModel.onEvent(RecipeEvent.SetRecipeActivityView(callReason!!))
        setContent {
            when(callReason) {
                RecipeActivityCall.FROM_NEW -> {
                    val state by recipesViewModel.state.collectAsState()
                    Log.i("NAME", state.recipeName)
                    RecipeView(state = state, onEvent = recipesViewModel::onEvent, context = this@RecipeActivity)
                }
                else -> {
                    val recipeId = intent.extras!!.getInt(RECIPE_ID_KEY_NAME)
                    recipesViewModel.onEvent(RecipeEvent.SetRecipeId(recipeId))
                    val state by recipesViewModel.state.collectAsState()
                    RecipeView(state = state, onEvent = recipesViewModel::onEvent, context = this@RecipeActivity)
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        recipesViewModel.onEvent(RecipeEvent.SetRecipeId(0))
    }

    companion object {
        const val RECIPE_ID_KEY_NAME = "RECIPE_ID"
        const val RECIPE_CALL_REASON_KEY_NAME = "RECIPE_CALL_REASON"
    }

}