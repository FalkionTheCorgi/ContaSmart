package com.example.accountspayable.BottomSheet.Ajustes

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Lifecycle
import com.example.accountspayable.DataStore.DataStore
import com.example.accountspayable.GoogleDrive.GoogleDriveService
import com.example.accountspayable.MainActivityViewModel
import com.example.accountspayable.OnLifecycleEvent
import com.example.accountspayable.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel


@Composable
fun BottomSheetAjustes(
    callBack: () -> Unit
) {

    val activityViewModel: MainActivityViewModel = koinViewModel()
    val dataStore: DataStore = get()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val notification = remember {
        mutableStateOf(
            NotificationManagerCompat.from(context).areNotificationsEnabled()
        )
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            notification.value = it
        }
    )
    val openDialog = remember {
        mutableStateOf(false)
    }
    val googleDrive : GoogleDriveService = get()
    val startForResult = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (result.data != null) {
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(intent)
                scope.launch {
                    activityViewModel.backupData.value = true
                    dataStore.saveBackupMode(true)
                }
                googleDrive.createFolderGoogleDrive()

                /**
                 * handle [task] result
                 */
            } else {
                scope.launch {
                    activityViewModel.backupData.value = false
                    dataStore.saveBackupMode(false)
                }
                Toast.makeText(context, R.string.toast_google_login_error, Toast.LENGTH_LONG).show()
            }
        }else{
            Log.d("CODE ERROR GOOGLE API", result.resultCode.toString())
        }
    }

    OnLifecycleEvent{ owner, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> { notification.value = NotificationManagerCompat.from(context).areNotificationsEnabled() }
            else                      -> { /* other stuff */ }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                text = stringResource(id = R.string.topbar_settings),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {

            Switch(
                checked = activityViewModel.darkMode.value,
                onCheckedChange = {
                    scope.launch {
                        dataStore.saveDarkMode(it)
                        activityViewModel.darkMode.value = it
                    }
                },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = MaterialTheme.colors.primary,
                    checkedThumbColor = MaterialTheme.colors.primary
                )
            )
            
            Text(
                text = stringResource(id = R.string.bottomsheet_ajustes_dark_mode),
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.padding(top = 12.dp)
            )

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {

            Checkbox(
                checked = activityViewModel.backupData.value,
                onCheckedChange = {
                    if(it) {
                        startForResult.launch(googleDrive.getGoogleSignInClient().signInIntent)
                    }else{
                        scope.launch {
                            activityViewModel.backupData.value = false
                            dataStore.saveBackupMode(false)
                        }
                    }
                }
            )

            Text(
                text = stringResource(id = R.string.bottomsheet_ajustes_save_data),
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.padding(top = 12.dp)
            )

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {

            Checkbox(
                checked = notification.value,
                onCheckedChange = {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && it) {

                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)

                    }else{
                        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                            .putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                        startActivity(context, intent, null)
                    }
                }
            )

            Text(
                text = stringResource(id = R.string.bottomsheet_ajustes_receive_notification),
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.padding(top = 12.dp)
            )

        }

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = { callBack.invoke() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
        ) {
            Text(text = stringResource(id = R.string.btn_close), color = MaterialTheme.colors.onSecondary)
        }

    }

    if(openDialog.value) {

        AlertPermissionStorage(
            accept = {
                openDialog.value = false
                activityViewModel.permissionStorage.value = true
            },
            decline = {
                openDialog.value = false
            }
        )

    }

}

@Composable
@Preview
fun PreviewBottomSheetAjustes(){

    BottomSheetAjustes(
        callBack = {}
    )

}
