package com.example.recipeapp.composables.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.recipeapp.RecipeActivity
import com.example.recipeapp.RecipeActivity.Companion.RECIPE_CALL_REASON_KEY_NAME
import com.example.recipeapp.RecipeActivity.Companion.RECIPE_ID_KEY_NAME
import com.example.recipeapp.database.events.RecipeEvent
import com.example.recipeapp.database.tables.Recipe
import com.example.recipeapp.enums.RecipeActivityView
import com.example.recipeapp.states.RecipeState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainView(
    state: RecipeState,
    onEvent: (RecipeEvent) -> Unit,
    context: Context
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Text("Recipe App", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "Home") },
                    selected = true,
                    onClick = {
                        scope.launch { drawerState.close() }

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
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    startActivity(context, Intent(context, RecipeActivity::class.java).apply {
                        putExtra(RECIPE_CALL_REASON_KEY_NAME, RecipeActivityView.NEW.stringify)
                    }, null)
                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add new Recipe")
                }
            },
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
                    }
                )
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
                        RecipeTile(recipe = recipe, context = context)
                    }
                }
            )
        }
    }

}

@Composable
fun RecipeTile(
    recipe: Recipe,
    context: Context
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clickable {
                startActivity(context, Intent(context, RecipeActivity::class.java).apply {
                    putExtra(RECIPE_ID_KEY_NAME, recipe.id)
                    putExtra(RECIPE_CALL_REASON_KEY_NAME, RecipeActivityView.DEFAULT.stringify)
                }, null)
            }
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