package com.example.recipeapp.composables.recipes

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.recipeapp.MainActivity
import com.example.recipeapp.RecipeActivity
import com.example.recipeapp.composables.CookingStepView
import com.example.recipeapp.database.events.RecipeEvent
import com.example.recipeapp.states.RecipeState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RecipeView(
    state: RecipeState,
    onEvent: (RecipeEvent) -> Unit,
    context: Context
) {
    val recipeName = state.recipeName
    val recipeIngredients = state.recipeIngredients
    val recipeSteps = state.recipeSteps

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Text("Recipe App", modifier = Modifier.padding(16.dp))
                androidx.compose.material3.Divider()
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
                MediumTopAppBar(
                    title = {
                        Text(text = recipeName)
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
                            //TODO
                        }) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit recipe")
                        }
                    }
                )
            }
        ) {

            Column {
                var stepsCounter = 0
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
                Row(

                ) {

                }
                LazyColumn(

                ) {
                    item { Text(text = "Cooking Steps") }
                    items(recipeSteps.steps) {
                            recipeStep ->
                        Row {
                            Column(

                            ) {
                                Text(text = "${++stepsCounter}")
                                Text(text = recipeStep.description)
                            }
                            Column(

                            ) {
                                Text(text = "Timer")
                                for (stepTimer in recipeStep.stepTimer) {
                                    Text(text = "timer")
                                    Column(

                                    ) {
                                        Row(

                                        ) {
                                            Text(text = stepTimer.name)
                                            Button(onClick = { /*TODO*/ }) {
                                                Image(imageVector = Icons.Default.PlayArrow, contentDescription = "Start timer")
                                            }
                                        }
                                        Text(text = stepTimer.toString2())
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}