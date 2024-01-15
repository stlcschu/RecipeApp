package com.example.recipeapp.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun DialogComposable(
    onDismissRequest : () -> Unit,
    onConfirmation : () -> Unit,
    content : @Composable() (() -> Unit)? = null,
    confirmText : String = "Confirm",
    dismissText : String = "Dismiss",
    headline : String? = null,
    message : String? = null,
    icon : ImageVector = Icons.Default.Info
) {
    if (content == null) {
        AlertDialog(
            icon = {
                   Icon(imageVector = icon, contentDescription = "Info icon")
            },
            title = {
                Text(text = headline ?: "")
            },
            text = {
                Text(text = message ?: "")
            },
            onDismissRequest = {
                onDismissRequest()
            },
            confirmButton = {
                TextButton(onClick = { onConfirmation() }) {
                    Text(text = confirmText)
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismissRequest() }) {
                    Text(text = dismissText)
                }
            }
        )
        return
    }
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card {
            content()
            Row(

            ) {
                Column(
                    horizontalAlignment = AbsoluteAlignment.Left
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(text = dismissText)
                    }
                }
                Column (
                    horizontalAlignment = AbsoluteAlignment.Right
                ) {
                    TextButton(
                        onClick = { onConfirmation() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(text = confirmText)
                    }
                }
            }
        }
    }
}