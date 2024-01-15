package com.example.recipeapp.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recipeapp.dataclasses.RecipeStep


@Composable
fun CookingStepView(recipeStep: RecipeStep, stepIndex: Int) {
    Row {
        Column(

        ) {
            Text(text = "$stepIndex")
            Text(text = recipeStep.description)
        }
        Column(

        ) {
            Text(text = "Timer")

        }
        // TODO: FIX
//        LazyColumn() {
//            items(recipeStep.stepTimer) {
//                timer ->
//                Text(text = timer.name)
//                    Button(onClick = { /*TODO*/ }) {
//                        Image(imageVector = Icons.Default.PlayArrow, contentDescription = "Start timer")
//                    }
//            }
//        }
//        for (stepTimer in recipeStep.stepTimer) {
//            Column(
//
//            ) {
//                Row(
//
//                ) {
//                    Text(text = stepTimer.name)
//                    Button(onClick = { /*TODO*/ }) {
//                        Image(imageVector = Icons.Default.PlayArrow, contentDescription = "Start timer")
//                    }
//                }
//                Text(text = stepTimer.toString2())
//            }
//        }
    }
}