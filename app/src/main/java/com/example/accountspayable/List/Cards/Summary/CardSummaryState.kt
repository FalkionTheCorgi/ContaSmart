package com.example.accountspayable.List.Cards.Summary

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.example.accountspayable.Data.GlobalVariables
import com.example.accountspayable.Room.DataBase
import com.example.accountspayable.Room.Summary.SummaryEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CardSummaryState(
    context: Context
) {

    val dataSummary: MutableStateFlow<SummaryEntity?> = MutableStateFlow(null)
    val expenditure = mutableStateOf(0.0)

    var priceOfPerson1 = mutableStateOf(0.0)
    var priceOfPerson2 = mutableStateOf(0.0)
    var priceOfPerson3 = mutableStateOf(0.0)
    var priceOfPerson4 = mutableStateOf(0.0)

}