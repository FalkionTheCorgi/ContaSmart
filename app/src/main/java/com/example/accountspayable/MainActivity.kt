package com.example.accountspayable

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import androidx.work.ExistingWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.accountspayable.BottomSheet.Ajustes.BottomSheetAjustes
import com.example.accountspayable.BottomSheet.BottomSheetSummary
import com.example.accountspayable.BottomSheet.Calendar.BottomSheetCalendar
import com.example.accountspayable.BottomSheet.Donation.BottomSheetDonation
import com.example.accountspayable.BottomSheet.Sugestao.BottomSheetSugestao
import com.example.accountspayable.DataStore.DataStore
import com.example.accountspayable.List.BottomSheetAddItem
import com.example.accountspayable.List.Cards.Item.AlertDialogCreateSummary
import com.example.accountspayable.List.Cards.Summary.CardSummaryViewModel
import com.example.accountspayable.Room.BackupDataBase
import com.example.accountspayable.TopBar.TopBarApp
import com.example.accountspayable.UpdateApp.InAppUpdate
import com.example.accountspayable.WorkManager.ExportDataBase
import com.example.accountspayable.ui.theme.AccountsPayableTheme
import com.example.gamesprices.Navigation.NavigationView
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.get
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.util.concurrent.TimeUnit


class MainActivity : ComponentActivity() {

    lateinit var firebaseAnalytics: FirebaseAnalytics
    @OptIn(ExperimentalMaterialApi::class)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val periodicWorkRequest = PeriodicWorkRequestBuilder<ExportDataBase>(1, TimeUnit.HOURS).build()
        WorkManager.getInstance(this).enqueue(
            periodicWorkRequest
        )

        firebaseAnalytics = Firebase.analytics


        setContent {

            val navController = rememberNavController()
            val inAppUpdate : InAppUpdate by inject { parametersOf(this) }
            val bottomSheetState =
                rememberModalBottomSheetState(
                    initialValue = ModalBottomSheetValue.Hidden,
                    confirmValueChange = { false },
                    skipHalfExpanded = true
                )
            val backupDataBase : BackupDataBase = get()
            val coroutineScope = rememberCoroutineScope()
            val model: MainActivityViewModel = koinViewModel()
            val cardSumModel: CardSummaryViewModel = koinViewModel()
            val dataStore: DataStore = get()
            val systemUiController = rememberSystemUiController()

            MobileAds.initialize(this) {}

            LaunchedEffect(true){

                if (!checkPermissionRWDisk(applicationContext) && !dataStore.getOpenFirstTime.first()) {
                    requestPermission()
                    backupDataBase.importarDataBase(applicationContext)
                    dataStore.saveOpenFirstTime(true)
                }

                model.darkMode.value = dataStore.getDarkMode.first()
                model.backupData.value = dataStore.getBackupMode.first()

                inAppUpdate.verify()

            }

            LaunchedEffect(model.bottomSheetType.value) {

                if (model.bottomSheetType.value != BottomSheetTypes.NONE)
                    bottomSheetState.show()

            }

            LaunchedEffect(model.permissionStorage.value) {

                if (model.permissionStorage.value && !checkPermissionRWDisk(applicationContext)) {

                    requestPermission()

                }

                model.permissionStorage.value = false

            }

            AccountsPayableTheme(
                darkTheme = model.darkMode.value
            ) {

                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = if (model.darkMode.value) { Color(0xFF121212L) } else { Color(0xFF003366L) },
                        darkIcons = false
                    )
                }

                ModalBottomSheetLayout(
                    sheetContent = {

                        when (model.bottomSheetType.value) {

                            BottomSheetTypes.ADD -> {
                                BottomSheetAddItem(
                                    callBack = {
                                        coroutineScope.launch {
                                            bottomSheetState.hide()
                                            model.bottomSheetType.value = BottomSheetTypes.NONE
                                        }
                                    }
                                )
                            }
                            BottomSheetTypes.EDIT -> {

                                BottomSheetAddItem(
                                    idItem = model.editCardObj.value.id,
                                    itemName = model.editCardObj.value.name,
                                    description = model.editCardObj.value.descricao,
                                    price = model.editCardObj.value.valor.toString(),
                                    iconSelected = model.editCardObj.value.icon,
                                    check1 = model.editCardObj.value.check1,
                                    check2 = model.editCardObj.value.check2,
                                    check3 = model.editCardObj.value.check3,
                                    check4 = model.editCardObj.value.check4,
                                    person1 = model.editCardObj.value.person1,
                                    person2 = model.editCardObj.value.person2,
                                    person3 = model.editCardObj.value.person3,
                                    person4 = model.editCardObj.value.person4,
                                    isEdit = true,
                                    callBack = {
                                        coroutineScope.launch {
                                            bottomSheetState.hide()
                                            model.bottomSheetType.value = BottomSheetTypes.NONE
                                        }
                                    }
                                )

                            }

                            BottomSheetTypes.SUMMARYADD -> {

                                BottomSheetSummary(
                                    callBack = {
                                        coroutineScope.launch {
                                            bottomSheetState.hide()
                                            model.bottomSheetType.value = BottomSheetTypes.NONE
                                        }
                                    }
                                )

                            }

                            BottomSheetTypes.SUMMARYEDIT -> {

                                BottomSheetSummary(
                                    isEdit = true,
                                    id = model.editCardSummary.value.id,
                                    revenue = model.editCardSummary.value.revenue.toString(),
                                    person1 = model.editCardSummary.value.person1,
                                    person2 = model.editCardSummary.value.person2,
                                    person3 = model.editCardSummary.value.person3,
                                    person4 = model.editCardSummary.value.person4,
                                    callBack = {
                                        coroutineScope.launch {
                                            bottomSheetState.hide()
                                            model.bottomSheetType.value = BottomSheetTypes.NONE
                                        }
                                    }
                                )

                            }

                            BottomSheetTypes.CALENDAR -> {

                                BottomSheetCalendar {
                                    coroutineScope.launch {
                                        bottomSheetState.hide()
                                        model.bottomSheetType.value = BottomSheetTypes.NONE
                                    }
                                }

                            }

                            BottomSheetTypes.SUGGESTION -> {

                                BottomSheetSugestao {
                                    coroutineScope.launch {
                                        bottomSheetState.hide()
                                        model.bottomSheetType.value = BottomSheetTypes.NONE
                                    }
                                }

                            }

                            BottomSheetTypes.DONATION -> {

                                BottomSheetDonation {
                                    coroutineScope.launch {
                                        bottomSheetState.hide()
                                        model.bottomSheetType.value = BottomSheetTypes.NONE
                                    }
                                }

                            }

                            BottomSheetTypes.SETTINGS -> {

                                BottomSheetAjustes(
                                    callBack = {
                                        coroutineScope.launch {
                                            bottomSheetState.hide()
                                            model.bottomSheetType.value = BottomSheetTypes.NONE
                                        }
                                    }
                                )

                            }

                            BottomSheetTypes.NONE -> {

                                BottomSheetAddItem(
                                    callBack = {
                                        coroutineScope.launch {
                                            bottomSheetState.hide()
                                            model.bottomSheetType.value = BottomSheetTypes.NONE
                                        }
                                    }
                                )
                            }

                        }


                    },
                    sheetState = bottomSheetState,
                    sheetShape = RoundedCornerShape(16.dp)
                ) {

                    Scaffold(
                        topBar = { TopBarApp(title = "ContaSmart") },
                        content = {
                            NavigationView(navController = navController)
                        },
                        floatingActionButton = {


                            FloatingActionButton(
                                onClick = {
                                    coroutineScope.launch {
                                        if (cardSumModel.state.dataSummary.isNotEmpty()) {
                                            model.bottomSheetType.value = BottomSheetTypes.ADD
                                            bottomSheetState.show()
                                        } else {
                                            model.openAlertCreateSummary.value = true
                                        }
                                    }
                                },
                                backgroundColor = MaterialTheme.colors.primaryVariant
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add a new group or item",
                                    tint = MaterialTheme.colors.onSecondary
                                )
                            }

                        },
                        floatingActionButtonPosition = FabPosition.End,
                        bottomBar = {

                            AndroidView(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                factory = { context ->
                                    AdView(context).apply {
                                        setAdSize(AdSize.BANNER)
                                        adUnitId = BuildConfig.ADUNIT_ID
                                        loadAd(AdRequest.Builder().build())
                                    }
                                }
                            )

                        }
                    )

                }

                if (model.openAlertCreateSummary.value) {


                    AlertDialogCreateSummary(
                        accept = {
                            model.bottomSheetType.value = BottomSheetTypes.SUMMARYADD
                            model.openAlertCreateSummary.value = false
                        },
                        decline = {
                            model.openAlertCreateSummary.value = false
                        }
                    )

                }
            }
        }
    }

    fun requestPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            //Android is 11(R) or above
            try {
                Log.d("TAG", "requestPermission: try")
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                storageActivityResultLauncher.launch(intent)

            }
            catch (e: Exception){
                Log.e("TAG", "requestPermission: ", e)
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                storageActivityResultLauncher.launch(intent)
            }
        }
        else{
            //Android is below 11(R)
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE),
                STORAGE_PERMISSION_CODE
            )
        }
    }

    //Verificar resposta e criar pasta em Androids acima do 11
    private val storageActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        Log.d(TAG, "storageActivityResultLauncher: ")
        //here we will handle the result of our intent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            //Android is 11(R) or above
            if (Environment.isExternalStorageManager()){
                //Manage External Storage Permission is granted
                Log.d(TAG, "storageActivityResultLauncher: Manage External Storage Permission is granted")
                createFolder()
            }
            else{
                //Manage External Storage Permission is denied....
                Log.d(TAG, "storageActivityResultLauncher: Manage External Storage Permission is denied....")
                //toast("Manage External Storage Permission is denied....")
            }
        }
        else{
            //Android is below 11(R)
        }
    }


    //Função depreciada ainda utilizada para Android's abaixo do 11
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty()){
                //check each permission if granted or not
                val write = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val read = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (write && read){
                    //External Storage Permission granted
                    Log.d(TAG, "onRequestPermissionsResult: External Storage Permission granted")
                    createFolder()
                }
                else{
                    //External Storage Permission denied...
                    Log.d(TAG, "onRequestPermissionsResult: External Storage Permission denied...")
                    print("External Storage Permission denied...")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val inAppUpdate : InAppUpdate by inject { parametersOf(this) }
        inAppUpdate.inProgressUpdate()
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AccountsPayableTheme {
        Greeting("Android")
    }
}