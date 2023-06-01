package com.example.accountspayable.BottomSheet.Ajustes

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.accountspayable.DataStore.DataStore
import com.example.accountspayable.MainActivityViewModel
import com.example.accountspayable.checkPermissionRWDisk
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
    var openDialog = remember {
        mutableStateOf(false)
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
                text = "Ajustes",
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
                text = "Modo Escuro",
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
                    if (checkPermissionRWDisk(context = context)) {
                        scope.launch {
                            activityViewModel.backupData.value = it
                            dataStore.saveBackupMode(it)
                        }
                    } else {
                        openDialog.value = true
                    }
                }
            )

            Text(
                text = "Salvar meus dados no celular",
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
            Text(text = "FECHAR", color = MaterialTheme.colors.onSecondary)
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
