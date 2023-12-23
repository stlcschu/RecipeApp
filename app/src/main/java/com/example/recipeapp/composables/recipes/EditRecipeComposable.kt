package com.example.recipeapp.composables.recipes

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.text.isDigitsOnly
import com.example.recipeapp.MainActivity
import com.example.recipeapp.database.events.RecipeEvent
import com.example.recipeapp.database.tables.Recipe
import com.example.recipeapp.dataclasses.EditingRecipe
import com.example.recipeapp.dataclasses.Ingredient
import com.example.recipeapp.dataclasses.Ingredients
import com.example.recipeapp.dataclasses.RecipeSteps
import com.example.recipeapp.enums.MeasuringUnit
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EditRecipeView(
    onEvent: (RecipeEvent) -> Unit,
    context: Context,
    recipe: EditingRecipe
) {

    var ingredientNames by remember { mutableStateOf(recipe.ingredientNames) }
    var ingredientAmounts by remember { mutableStateOf(recipe.ingredientAmounts) }
    var ingredientMeasuringUnits by remember { mutableStateOf(recipe.ingredientMeasurements) }
    var cookingSteps by remember { mutableStateOf(recipe.cookingSteps) }
    var recipeName by remember { mutableStateOf(recipe.recipeName) }
    val measuringUnits = MeasuringUnit.convertToList()
    var measuringUnitsDropDown by remember { mutableStateOf(recipe.measurementUnitDropDown) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Text("Recipe App", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "Home") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            startActivity(
                                context,
                                Intent(context, MainActivity::class.java),
                                null
                            )
                        }

                    }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Settings") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                    }
                )
            }
        },
        drawerState = drawerState
    ) {
        Scaffold (
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = "")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch { drawerState.open() }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            onEvent(
                                RecipeEvent.SetRecipeName(recipeName)
                            )

                            val ingredients = combineStatesToIngredientsList(ingredientNames, ingredientAmounts, ingredientMeasuringUnits)
                            onEvent(
                                RecipeEvent.SetRecipeIngredients(
                                    Ingredients(ingredients = ingredients, amountOfIngredients = ingredients.size)
                                )
                            )

                            onEvent(
                                RecipeEvent.SetRecipeSteps(
                                    RecipeSteps(steps = cookingSteps, amountOfSteps = cookingSteps.size)
                                )
                            )

                            onEvent(
                                RecipeEvent.SaveRecipe
                            )
                            startActivity(
                                context,
                                Intent(context, MainActivity::class.java),
                                null
                            )
                        }) {
                            Icon(imageVector = Icons.Default.Done, contentDescription = "Save Recipe")
                        }
                    }
                )
            }
        ) {
            Column() {
                OutlinedTextField(
                    value = recipeName,
                    onValueChange = {
                        recipeName = it
                    }
                )

                LazyColumn(

                ) {
                    item { Text(text = "Ingredients") }
                    itemsIndexed(ingredientNames) {
                            index, ingredientName ->
                        Row (

                        ) {

                            OutlinedTextField(
                                value = if (ingredientAmounts[index] == 0) "" else ingredientAmounts[index].toString(),
                                onValueChange = {
                                    if (it.isDigitsOnly()) {
                                        ingredientAmounts = changeIngredientsAmount(ingredientAmounts, it, index)
                                    }
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.width(90.dp)
                            )

                            ExposedDropdownMenuBox(
                                expanded = measuringUnitsDropDown[index],
                                onExpandedChange = {
                                    measuringUnitsDropDown = changeMeasurementUnitBoolean(measuringUnitsDropDown, index)
                                },
                                modifier = Modifier.width(100.dp)
                            ) {
                                TextField(
                                    readOnly = true,
                                    value = ingredientMeasuringUnits[index].stringify,
                                    onValueChange = { },
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = measuringUnitsDropDown[index]
                                        )
                                    },
                                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                                )
                                ExposedDropdownMenu(
                                    expanded = measuringUnitsDropDown[index],
                                    onDismissRequest = {
                                        measuringUnitsDropDown = changeMeasurementUnitBoolean(measuringUnitsDropDown, index, false)
                                    }
                                ) {

                                    measuringUnits.forEach { selectionOption ->
                                        DropdownMenuItem(
                                            onClick = {
                                                ingredientMeasuringUnits = changeIngredientMeasuringUnit(ingredientMeasuringUnits, selectionOption, index)
                                                measuringUnitsDropDown = changeMeasurementUnitBoolean(measuringUnitsDropDown, index, false)
                                            }
                                        ){
                                            Text(text = selectionOption.stringify)
                                        }
                                    }
                                }
                            }

                            OutlinedTextField(
                                value = ingredientName,
                                onValueChange = {
                                    ingredientNames = changeIngredientsName(ingredientNames, it, index)
                                },
                                singleLine = false,
                                minLines = 1,
                                maxLines = 4,
                                modifier = Modifier.width(200.dp)
                            )

                            Button(
                                onClick = {
                                    val (namesAndAmounts, unitsAndBool) = removeIngredient(ingredientNames, ingredientAmounts, ingredientMeasuringUnits, measuringUnitsDropDown, index)
                                    ingredientNames = namesAndAmounts.first
                                    ingredientAmounts = namesAndAmounts.second
                                    ingredientMeasuringUnits = unitsAndBool.first
                                    measuringUnitsDropDown = unitsAndBool.second
                                }
                            ) {
                                Icon(imageVector = Icons.Default.Clear, contentDescription = "Remove ingredients")
                            }

                        }
                    }
                    item {
                        Button(
                            onClick = {
                                val (namesAndAmounts, unitsAndBool) = addNewIngredient(ingredientNames, ingredientAmounts, ingredientMeasuringUnits, measuringUnitsDropDown)
                                ingredientNames = namesAndAmounts.first
                                ingredientAmounts = namesAndAmounts.second
                                ingredientMeasuringUnits = unitsAndBool.first
                                measuringUnitsDropDown = unitsAndBool.second
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
                                Button(
                                    onClick = {
                                        cookingSteps = removeRecipeStep(cookingSteps, index)
                                    }
                                ) {
                                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Remove cooking step")
                                }
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
    }
}

private fun addNewIngredient(
    ingredientNames: List<String>, ingredientAmounts: List<Int>,
    ingredientMeasuringUnits: List<MeasuringUnit>, measuringUnitsDropDown: List<Boolean>
) : Pair<Pair<List<String>, List<Int>>, Pair<List<MeasuringUnit>, List<Boolean>>> {
    val newIngredientNames = ingredientNames.toMutableList()
    val newIngredientAmounts = ingredientAmounts.toMutableList()
    val newIngredientMeasuringUnits = ingredientMeasuringUnits.toMutableList()
    val newMeasuringUnitsDropDown = measuringUnitsDropDown.toMutableList()
    newIngredientNames.add("")
    newIngredientAmounts.add(0)
    newIngredientMeasuringUnits.add(MeasuringUnit.NONE)
    newMeasuringUnitsDropDown.add(false)
    return Pair(Pair(newIngredientNames, newIngredientAmounts), Pair(newIngredientMeasuringUnits, newMeasuringUnitsDropDown))
}

private fun removeIngredient(
    ingredientNames: List<String>, ingredientAmounts: List<Int>,
    ingredientMeasuringUnits: List<MeasuringUnit>, measuringUnitsDropDown: List<Boolean>,
    index: Int
) : Pair<Pair<List<String>, List<Int>>, Pair<List<MeasuringUnit>, List<Boolean>>> {
    val newIngredientNames = ingredientNames.toMutableList()
    val newIngredientAmounts = ingredientAmounts.toMutableList()
    val newIngredientMeasuringUnits = ingredientMeasuringUnits.toMutableList()
    val newMeasuringUnitsDropDown = measuringUnitsDropDown.toMutableList()
    newIngredientNames.removeAt(index)
    newIngredientAmounts.removeAt(index)
    newIngredientMeasuringUnits.removeAt(index)
    newMeasuringUnitsDropDown.removeAt(index)
    return Pair(Pair(newIngredientNames, newIngredientAmounts), Pair(newIngredientMeasuringUnits, newMeasuringUnitsDropDown))
}

private fun changeIngredientsName(ingredientNames: List<String>, value: String, index: Int) : List<String> {
    val newIngredients = ingredientNames.toMutableList()
    newIngredients[index] = value
    return newIngredients
}

private fun changeIngredientsAmount(ingredientAmounts: List<Int>, value: String, index: Int) : List<Int> {
    val newIngredients = ingredientAmounts.toMutableList()
    newIngredients[index] =  if (value.isBlank()) 0 else if (value.toInt() > 1000000) newIngredients[index] else value.toInt()
    return newIngredients
}

private fun changeIngredientMeasuringUnit(ingredientMeasuringUnits: List<MeasuringUnit>, value: MeasuringUnit, index: Int) : List<MeasuringUnit> {
    val newIngredients = ingredientMeasuringUnits.toMutableList()
    newIngredients[index] =  value
    return newIngredients
}

private fun addNewRecipeStep(recipeSteps: List<String>) : List<String> {
    val newRecipeSteps = recipeSteps.toMutableList()
    newRecipeSteps.add("")
    return newRecipeSteps
}

private fun removeRecipeStep(recipeSteps: List<String>, index: Int) : List<String> {
    val newRecipeSteps = recipeSteps.toMutableList()
    newRecipeSteps.removeAt(index)
    return newRecipeSteps
}

private fun changeCookingStepDescription(recipeSteps: List<String>, value: String, index: Int) : List<String> {
    val newRecipeSteps = recipeSteps.toMutableList()
    newRecipeSteps[index] = value
    return newRecipeSteps
}

private fun changeMeasurementUnitBoolean(measuringUnitsDropDown: List<Boolean>, index: Int, switchSates: Boolean = true) : List<Boolean> {
    val newMeasuringUnitsDropDown = measuringUnitsDropDown.toMutableList()
    newMeasuringUnitsDropDown[index] = if (switchSates) !newMeasuringUnitsDropDown[index] else false
    return newMeasuringUnitsDropDown
}

private fun combineStatesToIngredientsList(ingredientsNames: List<String>, ingredientsAmount: List<Int>, ingredientsMeasuringUnit: List<MeasuringUnit>) : List<Ingredient> {
    val ingredientsList = mutableListOf<Ingredient>()
    for (i in ingredientsNames.indices){
        ingredientsList.add(
            Ingredient(
                name = ingredientsNames[i],
                amount = ingredientsAmount[i],
                measuringUnit = ingredientsMeasuringUnit[i]
            )
        )
    }
    return ingredientsList
}

