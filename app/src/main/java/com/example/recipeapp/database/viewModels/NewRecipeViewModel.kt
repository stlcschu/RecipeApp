package com.example.recipeapp.database.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.database.daos.RecipeDao
import com.example.recipeapp.database.events.RecipeEvent
import com.example.recipeapp.database.tables.Recipe
import com.example.recipeapp.dataclasses.Ingredients
import com.example.recipeapp.dataclasses.RecipeSteps
import com.example.recipeapp.states.RecipeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewRecipeViewModel(
    private val recipeDao: RecipeDao
) : ViewModel() {

    private val _state = MutableStateFlow(RecipeState())

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

            else -> {}
        }
    }

}