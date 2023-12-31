package com.example.recipeapp.database.viewModels

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.database.daos.RecipeDao
import com.example.recipeapp.database.events.RecipeEvent
import com.example.recipeapp.database.tables.Recipe
import com.example.recipeapp.dataclasses.Ingredients
import com.example.recipeapp.dataclasses.RecipeSteps
import com.example.recipeapp.enums.RecipeActivityCall
import com.example.recipeapp.states.RecipeState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val recipeDao: RecipeDao
) : ViewModel() {

    private val _recipeId = mutableIntStateOf(0)

    private val _recipeActivityView = MutableStateFlow(RecipeActivityCall.FROM_DEFAULT)

    @OptIn(ExperimentalCoroutinesApi::class)
    val recipe = _recipeActivityView
        .flatMapLatest { mRecipeActivityView ->
            when (mRecipeActivityView) {
                RecipeActivityCall.FROM_NEW -> {
                    recipeDao.getLatestAddedRecipe()
                }
                else -> {
                    recipeDao.getSingleRecipesById(_recipeId.intValue)
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Recipe())

    private val _state = MutableStateFlow(RecipeState())

    val state = combine(_state, recipe) { state, recipe ->
        state.copy (
            recipeName = if(_recipeActivityView.value == RecipeActivityCall.FROM_NEW) recipe.name else if(_recipeId.intValue <= 0) "" else recipe.name,
            recipeIngredients = if(_recipeActivityView.value == RecipeActivityCall.FROM_NEW) recipe.ingredients else if(_recipeId.intValue <= 0) Ingredients() else recipe.ingredients,
            recipeSteps = if(_recipeActivityView.value == RecipeActivityCall.FROM_NEW) recipe.steps else if(_recipeId.intValue <= 0) RecipeSteps() else recipe.steps
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), RecipeState())

    fun onEvent(event: RecipeEvent) {
        when(event) {
            is RecipeEvent.DeleteRecipe -> {
                viewModelScope.launch {
                    recipeDao.deleteRecipe(event.recipe)
                }
            }

            RecipeEvent.SaveRecipe -> {

                val recipeName = _state.value.recipeName
                val recipeIngredients = _state.value.recipeIngredients
                val recipeSteps = _state.value.recipeSteps

                if (recipeName.isBlank()) return
                val recipe = Recipe(
                    name = recipeName,
                    ingredients = recipeIngredients,
                    steps = recipeSteps
                )

                viewModelScope.launch {
                    recipeDao.upsertRecipe(recipe)
                }

                _state.update {
                    it.copy(
                        recipeName = "",
                        recipeIngredients = Ingredients(),
                        recipeSteps = RecipeSteps()
                    )
                }

            }

            is RecipeEvent.SetRecipeName -> {
                _state.update { it.copy(
                    recipeName = event.recipeName
                ) }
            }

            is RecipeEvent.SetRecipeIngredients -> {
                _state.update { it.copy(
                    recipeIngredients = event.recipeIngredients
                ) }
            }
            is RecipeEvent.SetRecipeSteps -> {
                _state.update { it.copy(
                    recipeSteps = event.recipeSteps
                ) }
            }

            is RecipeEvent.SetRecipeId -> {
                _recipeId.intValue = event.recipeId
            }

            is RecipeEvent.SetRecipeActivityView -> {
                _recipeActivityView.value = event.recipeActivityView
            }

            else -> {}
        }
    }

}