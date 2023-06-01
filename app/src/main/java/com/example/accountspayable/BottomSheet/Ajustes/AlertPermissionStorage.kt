package com.example.accountspayable.BottomSheet.Ajustes

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun AlertPermissionStorage(
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
                    text = "Aviso",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.secondary
                )
            }
        },
        containerColor = MaterialTheme.colors.background,
        text = {
            Text(
                text = "É preciso ativar a permissão para o aplicativo salvar os seus dados",
                color = MaterialTheme.colors.secondary
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    accept()
                }
            ) {
                Text("Aceitar", color = MaterialTheme.colors.primary)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    decline()
                }
            ) {
                Text("Cancelar", color = Color.Red)
            }
        }

    )



}