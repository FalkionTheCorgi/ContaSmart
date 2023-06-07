package com.example.accountspayable.List.Cards.Summary

import androidx.compose.material.MaterialTheme
import androidx.compose.material3.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun AlertDialogSummary(
    accept: () -> Unit,
    decline: () -> Unit
){

    AlertDialog(
        onDismissRequest = { decline() },
        title = {
            Text(
                text = "Aviso",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colors.secondary
            )
        },
        containerColor = MaterialTheme.colors.background,
        text = {
            Text(
                text = "Ao deletar o sumário todos os itens também serão deletados. Você tem certeza?",
                color = MaterialTheme.colors.secondary
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    accept()
                }
            ) {
                Text("Confirmar", color = MaterialTheme.colors.primary)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    decline()
                }
            ) {
                Text("Cancelar", color = MaterialTheme.colors.error)
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