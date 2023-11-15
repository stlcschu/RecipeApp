package com.example.recipeapp.composables.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.recipeapp.database.events.RecipeEvent
import com.example.recipeapp.states.RecipeState

@Composable
fun RecipeView(
    state: RecipeState,
    onEvent: (RecipeEvent) -> Unit
) {
    val recipeName = state.recipeName
    val recipeIngredients = state.recipeIngredients
    val recipeSteps = state.recipeSteps
    Column {
        var stepsCounter = 0
        Text(text = recipeName)
        LazyColumn(

        ) {
            item { Text(text = "Ingredients") }
            items(recipeIngredients.ingredients) {
                recipeIngredient ->
                Row {
                    Text(text = recipeIngredient.amount.toString())
                    Text(text = recipeIngredient.measuringUnit.stringify)
                    Text(text = recipeIngredient.name)
                }
            }
        }
        Divider()
        LazyColumn(

        ) {
            item { Text(text = "Cooking Steps") }
            items(recipeSteps.steps) {
                    recipeStep ->
                Row {
                    Text(text = "${++stepsCounter}")
                    Text(text = recipeStep)
                }
            }
        }
    }
}