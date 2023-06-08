package com.example.accountspayable.List.Cards.Item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.accountspayable.R

@Composable
fun AlertDialogCreateSummary(
    accept: () -> Unit,
    decline: () -> Unit
){

    AlertDialog(
        onDismissRequest = { decline() },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.notice),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.secondary
                )
            }
        },
        containerColor = MaterialTheme.colors.background,
        text = {
            Text(
                text = stringResource(id = R.string.subtitle_create_summary),
                color = MaterialTheme.colors.secondary
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    accept()
                }
            ) {
                Text(stringResource(id = R.string.btn_create), color = MaterialTheme.colors.primary)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    decline()
                }
            ) {
                Text(stringResource(id = R.string.btn_cancel), color = Color.Red)
            }
        }

    )



}