package com.example.accountspayable.List.Cards.Summary

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.accountspayable.Room.Data.DataSummary

class CardSummaryState {

    val dataSummary = mutableStateListOf<DataSummary>()

    var priceOfPerson1 = mutableStateOf(0.0)
    var priceOfPerson2 = mutableStateOf(0.0)
    var priceOfPerson3 = mutableStateOf(0.0)
    var priceOfPerson4 = mutableStateOf(0.0)

}