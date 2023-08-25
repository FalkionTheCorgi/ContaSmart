package com.example.accountspayable.BottomSheet.Summary

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.accountspayable.R
import com.example.accountspayable.Room.Data.DataSummary

class BottomSheetSumaryState(
    application: Application
) {

    var itemName = mutableStateOf("")
    var revenue = mutableStateOf("")
    var person1 = mutableStateOf("")
    var person2 = mutableStateOf("")
    var person3 = mutableStateOf("")
    var person4 = mutableStateOf("")
    var redFieldRevenue = mutableStateOf(false)

    var description = mutableStateOf("")
    var textButton = mutableStateOf(application.applicationContext.getString(R.string.btn_save))
    var progressBtn = mutableStateOf(false)

}