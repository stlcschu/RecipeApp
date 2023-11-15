package com.example.recipeapp.composables.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.recipeapp.database.events.RecipeEvent
import com.example.recipeapp.dataclasses.Ingredient
import com.example.recipeapp.dataclasses.Ingredients
import com.example.recipeapp.dataclasses.RecipeSteps
import com.example.recipeapp.enums.MeasuringUnit
import com.example.recipeapp.states.RecipeState

@Composable
fun NewEditRecipeView(
    onEvent: (RecipeEvent) -> Unit
) {

    var ingredients by remember { mutableStateOf(listOf<Ingredient>()) }
    var cookingSteps by remember { mutableStateOf(listOf<String>()) }
    var recipeName by remember { mutableStateOf("") }
    Column() {
        Button(
            onClick = {
                onEvent(
                    RecipeEvent.SetRecipeName(recipeName)
                )

                onEvent(
                    RecipeEvent.SetRecipeIngredients(Ingredients(ingredients = ingredients, amountOfIngredients = ingredients.size))
                )

                onEvent(
                    RecipeEvent.SetRecipeSteps(RecipeSteps(steps = cookingSteps, amountOfSteps = cookingSteps.size))
                )

                onEvent(
                    RecipeEvent.SaveRecipe
                )
            }
        ) {
            Text(text = "Save Recipe")
        }

        OutlinedTextField(
            value = recipeName,
            onValueChange = {
                recipeName = it
            }
        )

        LazyColumn(

        ) {
            item { Text(text = "Ingredients") }
            itemsIndexed(ingredients) {
                    index, ingredient ->
                Row (

                ) {
                    OutlinedTextField(
                        value = ingredient.name,
                        onValueChange = {
                            ingredients[index].name = it
                        }
                    )

                    OutlinedTextField(
                        value = ingredient.name,
                        onValueChange = {
                            ingredients[index].amount = it.toInt()
                        }
                    )
                    Button(
                        onClick = {
                            ingredients = removeIngredients(ingredients, index)
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Remove ingredients")
                    }
                }
            }
            item {
                Button(
                    onClick = {
                        ingredients = addNewIngredients(ingredients)
                    }
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add new ingredient")
                    Text(text = "Add new ingredient")
                }
            }
        }

        LazyColumn(

        ) {
            item { Text(text = "Koch Schritte") }
            itemsIndexed(cookingSteps) {
                    index, step ->
                Column {
                    Text(text = "${index + 1}")
                    Row {
                        OutlinedTextField(
                            value = step,
                            onValueChange = {
                                cookingSteps = changeCookingStepDescription(cookingSteps, it, index)
                            },
                            singleLine = false,
                            minLines = 1
                        )
                    }
                    Button(
                        onClick = {
                            cookingSteps = removeRecipeStep(cookingSteps, index)
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Remove cooking step")
                    }
                }
            }
            item {
                Button(
                    onClick = {
                        cookingSteps = addNewRecipeStep(cookingSteps)
                    }
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add new cooking step")
                    Text(text = "Add new step")
                }
            }
        }
    }
}

private fun addNewIngredients(ingredients: List<Ingredient>) : List<Ingredient> {
    val newIngredients = ingredients.toMutableList()
    newIngredients.add(Ingredient(name = "", amount = 0, measuringUnit = MeasuringUnit.NONE))
    return newIngredients.toList()
}

private fun removeIngredients(ingredients: List<Ingredient>, index: Int) : List<Ingredient> {
    val newIngredients = ingredients.toMutableList()
    newIngredients.removeAt(index)
    return newIngredients.toList()
}

private fun addNewRecipeStep(recipeSteps: List<String>) : List<String> {
    val newRecipeSteps = recipeSteps.toMutableList()
    newRecipeSteps.add("")
    return newRecipeSteps.toList()
}

private fun removeRecipeStep(recipeSteps: List<String>, index: Int) : List<String> {
    val newRecipeSteps = recipeSteps.toMutableList()
    newRecipeSteps.removeAt(index)
    return newRecipeSteps.toList()
}

private fun changeCookingStepDescription(recipeSteps: List<String>, value: String, index: Int) : List<String> {
    val newRecipeSteps = recipeSteps.toMutableList()
    newRecipeSteps[index] = value
    return newRecipeSteps.toList()
}