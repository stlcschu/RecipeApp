package com.example.recipeapp.composables

import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun AddTimerComposable(
    timerValue: Float,
    labelValue: String,
    onTimerValueChanged: (Float) -> Unit,
    onLabelValueChanged: (String) -> Unit
) {

    OutlinedTextField(
        value = labelValue,
        onValueChange = onLabelValueChanged
    )
    Slider(
        value = timerValue,
        onValueChange = onTimerValueChanged,
        steps = 180,
        valueRange = 0f..180f
    )
    Text(text = convertValueToTextStyle(timerValue))
}

private fun convertValueToTextStyle(value: Float) : String {
    return if(value == 180f) "3 Stunden 0 Minuten" else if (value >= 120) "2 Stunden ${value - 120} Minuten" else if(value >= 60) "1 Stunde ${value - 60}" else "0 Stunden ${value} Minuten"
}

