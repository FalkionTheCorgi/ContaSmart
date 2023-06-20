package com.example.accountspayable.List.Cards.Summary

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.example.accountspayable.Data.GlobalVariables
import com.example.accountspayable.Room.DataBase
import com.example.accountspayable.Room.Summary.SummaryEntity
import kotlinx.coroutines.flow.Flow

class CardSummaryState(
    context: Context
) {

    var dataSummary: Flow<SummaryEntity?> = DataBase.getDataBase(context).summary().getASummaryByMonthAndYear(
        month = GlobalVariables.monthSelected.value ?: 1,
        year = GlobalVariables.yearSelected.value ?: 2023
    )

    var priceOfPerson1 = mutableStateOf(0.0)
    var priceOfPerson2 = mutableStateOf(0.0)
    var priceOfPerson3 = mutableStateOf(0.0)
    var priceOfPerson4 = mutableStateOf(0.0)

}