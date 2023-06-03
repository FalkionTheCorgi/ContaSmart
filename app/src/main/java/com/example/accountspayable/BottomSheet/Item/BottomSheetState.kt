package com.example.accountspayable.BottomSheet.Item

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.accountspayable.Room.Data.DataSummary


class BottomSheetState {

    var itemName = mutableStateOf("")
    var itemValue = mutableStateOf("")
    var description = mutableStateOf("")
    var textButton = mutableStateOf("SALVAR")
    var progressBtn = mutableStateOf(false)
    var listItemRadioButton = mutableListOf("Light", "Water", "Market", "Router", "Card", "Restaurant", "Phone", "House", "Game", "Other")
    var checkPerson1 = mutableStateOf(false)
    var checkPerson2 = mutableStateOf(false)
    var checkPerson3 = mutableStateOf(false)
    var checkPerson4 = mutableStateOf(false)
    val dataSummary = mutableStateListOf<DataSummary>()
    var iconOption = mutableStateOf("Light")
    var person1 = mutableStateOf("")
    var person2 = mutableStateOf("")
    var person3 = mutableStateOf("")
    var person4 = mutableStateOf("")
    var itemDeadline = mutableStateOf("")

    var redFieldItemName = mutableStateOf(false)
    var redFieldItemValue = mutableStateOf(false)
    var redFieldItemDeadline = mutableStateOf(false)


}