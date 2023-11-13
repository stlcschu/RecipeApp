package com.example.recipeapp.composables.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recipeapp.database.events.RecipeEvent
import com.example.recipeapp.database.tables.Recipe
import com.example.recipeapp.dataclasses.Ingredient
import com.example.recipeapp.dataclasses.Ingredients
import com.example.recipeapp.dataclasses.RecipeSteps
import com.example.recipeapp.enums.MeasuringUnit
import com.example.recipeapp.states.RecipeState

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScaffold(
    state: RecipeState,
    onEvent: (RecipeEvent) -> Unit
) {
    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(RecipeEvent.SetRecipeName("Test"))
                onEvent(RecipeEvent.SetRecipeIngredients(
                    Ingredients(
                    ingredients = listOf(
                        Ingredient(
                            name = "Apfel",
                            amount = 2,
                            measuringUnit = MeasuringUnit.NONE,
                        ),
                        Ingredient(
                            name = "Birne",
                            amount = 4,
                            measuringUnit = MeasuringUnit.NONE,
                        ),
                    ),
                    amountOfIngredients = 2
                )
                ))
                onEvent(RecipeEvent.SetRecipeSteps(
                    RecipeSteps(
                    steps = listOf("test1","test2","test4"),
                    amountOfSteps = 3,
                    timeNeededInMinutes = 45
                )
                ))
                onEvent(RecipeEvent.SaveRecipe)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add new Recipe")
            }
        }
    ) {
        val recipes = state.recipes
        if (recipes.isEmpty()) {
            NoRecipesInDatabase()
            return@Scaffold
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize(),
            content = {
                items(recipes) {recipe ->
                    RecipeTile(recipe = recipe)
                }
            }
        )
    }
}

@Composable
fun RecipeTile(recipe: Recipe) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
    ) {
        Text(text = recipe.name)
        Row {
            Column {
                Text(text = "${recipe.steps.amountOfSteps} Arbeitsschritte")
                Text(text = "Dauer: ${recipe.steps.timeNeededInMinutes} Minuten")
            }
            Column {
                Text(text = "${recipe.ingredients.amountOfIngredients} Zutaten")
            }
        }
    }
}

@Composable
fun NoRecipesInDatabase() {
    Text(text = "Keine Rezepte gefunden")
}