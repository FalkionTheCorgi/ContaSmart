package com.example.accountspayable.BottomSheet.Summary

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.accountspayable.Room.Data.DataSummary

class BottomSheetSumaryState {

    var itemName = mutableStateOf("")
    var itemValue = mutableStateOf("")
    var revenue = mutableStateOf("")
    var person1 = mutableStateOf("")
    var person2 = mutableStateOf("")
    var person3 = mutableStateOf("")
    var person4 = mutableStateOf("")

    var description = mutableStateOf("")
    var textButton = mutableStateOf("SALVAR")
    var progressBtn = mutableStateOf(false)

}