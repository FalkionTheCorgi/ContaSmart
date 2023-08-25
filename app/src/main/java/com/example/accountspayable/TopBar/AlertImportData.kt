package com.example.accountspayable.TopBar

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.accountspayable.GoogleDrive.GoogleDriveService
import com.example.accountspayable.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import org.koin.androidx.compose.get

@Composable
fun AlertImportData(
    accept: () -> Unit,
    decline: () -> Unit
){
    val context = LocalContext.current
    val googleDrive : GoogleDriveService = get()
    val startForResult = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (result.data != null) {
                accept()
                //utilizado para extrair dados da conta não sensiveis caso assim seja necessário
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(intent)
            } else {
                Toast.makeText(context, R.string.toast_google_login_error, Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(context, R.string.toast_google_login_error, Toast.LENGTH_LONG).show()
            Log.d("CODE ERROR GOOGLE API", result.resultCode.toString())
        }
    }

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
                text = stringResource(id = R.string.alert_import_data_subtitle),
                color = MaterialTheme.colors.secondary
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    startForResult.launch(googleDrive.getGoogleSignInClient().signInIntent)
                }
            ) {
                Text(stringResource(id = R.string.btn_allow), color = MaterialTheme.colors.primary)
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