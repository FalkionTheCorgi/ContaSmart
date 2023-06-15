package com.example.accountspayable.BottomSheet.Ajustes

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.accountspayable.GoogleDrive.GoogleDriveService
import com.example.accountspayable.MainActivityViewModel
import com.example.accountspayable.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel

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
                text = stringResource(id = R.string.alert_permission_storage_subtext),
                color = MaterialTheme.colors.secondary
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    accept()
                }
            ) {
                Text(stringResource(id = R.string.btn_accept), color = MaterialTheme.colors.primary)
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