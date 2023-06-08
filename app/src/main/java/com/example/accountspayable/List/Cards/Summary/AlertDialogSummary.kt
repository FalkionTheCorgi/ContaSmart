package com.example.accountspayable.List.Cards.Summary

import androidx.compose.material.MaterialTheme
import androidx.compose.material3.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.accountspayable.R

@Composable
fun AlertDialogSummary(
    accept: () -> Unit,
    decline: () -> Unit
){

    AlertDialog(
        onDismissRequest = { decline() },
        title = {
            Text(
                text = stringResource(id = R.string.notice),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colors.secondary
            )
        },
        containerColor = MaterialTheme.colors.background,
        text = {
            Text(
                text = stringResource(id = R.string.subtitle_summary),
                color = MaterialTheme.colors.secondary
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    accept()
                }
            ) {
                Text(stringResource(id = R.string.btn_confirm), color = MaterialTheme.colors.primary)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    decline()
                }
            ) {
                Text(stringResource(id = R.string.btn_cancel), color = MaterialTheme.colors.error)
            }
        }

    )



}

@Composable
@Preview
fun PreviewAlertDialogSummary(){

    AlertDialogSummary(
        accept = {},
        decline = {}
    )

}