package com.example.accountspayable

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import java.io.File
import java.time.LocalDateTime
import java.time.YearMonth
import java.util.*


val STORAGE_PERMISSION_CODE = 100
val NOTIFICATION_CHANNEL_ID = "NotificationDeadline"
val NOTIFICATION_ID = 0

fun getTodayDate(): LocalDateTime? {

    val calendar = Calendar.getInstance()
    calendar.add(Calendar.MONTH, 1)

    return LocalDateTime.of(
        LocalDateTime.now().year,
        LocalDateTime.now().month,
        LocalDateTime.now().dayOfMonth,
        LocalDateTime.now().hour,
        LocalDateTime.now().minute,
        LocalDateTime.now().second
    )


}

@Composable
fun getItemDropDown(str: String) : String {

    return when(str) {

        "Water" -> {
            stringResource(id = R.string.bottomsheet_item_water_account)
        }

        "Light" -> {
            stringResource(id = R.string.bottomsheet_item_light_account)
        }

        "Market" -> {
            stringResource(id = R.string.bottomsheet_item_supermarket)
        }

        "Router" -> {
            stringResource(id = R.string.bottomsheet_item_router)
        }

        "Card" -> {
            stringResource(id = R.string.bottomsheet_item_card)
        }

        "Restaurant" -> {
            stringResource(id = R.string.bottomsheet_item_restaurant)
        }

        "Phone" -> {
            stringResource(id = R.string.bottomsheet_item_phone)
        }

        "House" -> {
            stringResource(id = R.string.bottomsheet_item_house)
        }

        "Game" -> {
            stringResource(id = R.string.bottomsheet_item_game)
        }

        "Other" -> {
            stringResource(id = R.string.bottomsheet_item_other)
        }

        else -> {
            stringResource(id = R.string.bottomsheet_item_other)
        }

    }

}

@Composable
fun getPainterIcon(str: String): Painter {

    return when(str) {

        "Water" -> {
            painterResource(R.drawable.icon_water)
        }

        "Light" -> {
            painterResource(R.drawable.icon_luz)
        }

        "Market" -> {
            painterResource(R.drawable.icon_market)
        }

        "Router" -> {
            painterResource(R.drawable.icon_router)
        }

        "Card" -> {
            painterResource(R.drawable.icon_card)
        }

        "Restaurant" -> {
            painterResource(R.drawable.icon_restaurant)
        }

        "House" -> {
            painterResource(id = R.drawable.icon_house)
        }

        "Game" -> {
            painterResource(R.drawable.icon_game)
        }
        
        "Phone" -> {
            painterResource(id = R.drawable.icon_phone)
        }

        "Other" -> {
            painterResource(R.drawable.icon_other)
        }

        else -> {
            painterResource(R.drawable.icon_other)
        }

    }


}
@Composable
fun getTypeIcon(str: String): String {

    return when(str) {

        "Water" -> {
            stringResource(id = R.string.water)
        }

        "Light" -> {
            stringResource(id = R.string.light)
        }

        "Market" -> {
            stringResource(id = R.string.market)
        }

        "Router" -> {
            stringResource(id = R.string.router)
        }

        "Card" -> {
            stringResource(id = R.string.card)
        }

        "Restaurant" -> {
            stringResource(id = R.string.restaurant)
        }

        "House" -> {
            stringResource(id = R.string.house)
        }

        "Game" -> {
            stringResource(id = R.string.game)
        }

        "Phone" -> {
            stringResource(id = R.string.phone)
        }

        "Other" -> {
            stringResource(id = R.string.other)
        }

        else -> {
            stringResource(id = R.string.other)
        }

    }


}

fun returnMonthString(
    context: Context,
    number: Int
): String{

  return when(number){

      1 ->  context.getString(R.string.january)
      2 ->  context.getString(R.string.february)
      3 ->  context.getString(R.string.march)
      4 ->  context.getString(R.string.april)
      5 ->  context.getString(R.string.may)
      6 ->  context.getString(R.string.june)
      7 ->  context.getString(R.string.july)
      8 ->  context.getString(R.string.august)
      9 ->  context.getString(R.string.september)
      10 -> context.getString(R.string.october)
      11 -> context.getString(R.string.november)
      12 -> context.getString(R.string.december)
      else -> context.getString(R.string.january)

  }

}

fun createFolder(){
    //folder name
    val folderName = "conta_smart"

    //create folder using name we just input
    val file = File("${Environment.getExternalStorageDirectory()}/$folderName")
    //create folder
    val folderCreated = file.mkdir()

    //show if folder created or not
    if (folderCreated) {
        print("Folder Created: ${file.absolutePath}")
    } else {
        print("Folder not created....")
    }
}

fun checkPermissionRWDisk(
    context: Context
): Boolean{
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
        //Android is 11(R) or above
        Environment.isExternalStorageManager()
    }
    else{
        //Android is below 11(R)
        val write = ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val read = ContextCompat.checkSelfPermission(context.applicationContext, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED
    }
}

fun getDaysInMonth(): Int {

    val x = getTodayDate()
    val yearMonthObject = YearMonth.of(x?.year ?: 2023, x?.month)

    return yearMonthObject.lengthOfMonth() - 1

}

@Composable
fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

fun restartApp(context: Context){

    val packageManager: PackageManager = context.packageManager
    val intent: Intent = packageManager.getLaunchIntentForPackage(context.packageName)!!
    val componentName: ComponentName = intent.component!!
    val restartIntent: Intent = Intent.makeRestartActivityTask(componentName)
    context.startActivity(restartIntent)
    Runtime.getRuntime().exit(0)

}
