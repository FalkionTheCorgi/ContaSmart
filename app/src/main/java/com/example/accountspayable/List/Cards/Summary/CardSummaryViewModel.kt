package com.example.accountspayable.List.Cards.Summary

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.accountspayable.Data.GlobalVariables
import com.example.accountspayable.R
import com.example.accountspayable.Room.Data.DataSummary
import com.example.accountspayable.Room.Data.MonthYear
import com.example.accountspayable.Room.DataBase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.math.ceil

class CardSummaryViewModel(
    context: Context
) : ViewModel() {

    val state = CardSummaryState(
        context = context
    )

    suspend fun onAppearCardSummary(
        context: Context,
        month: Int,
        year: Int
    ){

        updateAllValuesOfPerson(
            context = context,
            month = month,
            year = year
        )

    }

    suspend fun updateAllValuesOfPerson(
        context: Context,
        month: Int,
        year: Int
    ) {

        updateValueOfPerson1(
            context = context,
            month = month,
            year = year
        )

        updateValueOfPerson2(
            context = context,
            month = month,
            year = year
        )

        updateValueOfPerson3(
            context = context,
            month = month,
            year = year
        )

        updateValueOfPerson4(
            context = context,
            month = month,
            year = year
        )

    }

    suspend fun updateValueOfPerson1(
        context: Context,
        month: Int,
        year: Int
    ){

        state.priceOfPerson1.value = 0.0

        val dataBase = DataBase.getDataBase(context).item()

        val list = dataBase.getAllItemsByPersonChecked1(
            month = month,
            year = year,
            checkedPerson1 = false,
            person1 = state.dataSummary.first()?.person1 ?: ""
         )

        list.forEach {

            state.priceOfPerson1.value += (Math.round(it.priceOfPerson * 100.0) / 100.0)
        }



    }

    suspend fun updateValueOfPerson2(
        context: Context,
        month: Int,
        year: Int
    ){

        state.priceOfPerson2.value = 0.0

        val dataBase = DataBase.getDataBase(context).item()

        val list = dataBase.getAllItemsByPersonChecked2(
            month = month,
            year = year,
            checkedPerson2 = false,
            person2 = state.dataSummary.first()?.person2 ?: ""
        )

        list.forEach {

            state.priceOfPerson2.value += (Math.round(it.priceOfPerson * 100.0) / 100.0)

        }



    }

    suspend fun updateValueOfPerson3(
        context: Context,
        month: Int,
        year: Int
    ){

        state.priceOfPerson3.value = 0.0

        val dataBase = DataBase.getDataBase(context).item()

        val list = dataBase.getAllItemsByPersonChecked3(
            month = month,
            year = year,
            checkedPerson3 = false,
            person3 = state.dataSummary.first()?.person3 ?: ""
        )

        list.forEach {

            state.priceOfPerson3.value += (Math.round(it.priceOfPerson * 100.0) / 100.0)

        }



    }

    suspend fun updateValueOfPerson4(
        context: Context,
        month: Int,
        year: Int
    ){

        state.priceOfPerson4.value = 0.0

        val dataBase = DataBase.getDataBase(context).item()

        val list = dataBase.getAllItemsByPersonChecked4(
            month = month,
            year = year,
            checkedPerson4 = false,
            person4 = state.dataSummary.first()?.person4 ?: ""
        )

        list.forEach {

            state.priceOfPerson4.value += (Math.round(it.priceOfPerson * 100.0) / 100.0)

        }



    }

    suspend fun deleteSummaryAndItens(
        context: Context,
        month: Int,
        year: Int,
        success: () -> Unit
    ) {

        val dataBaseItem = DataBase.getDataBase(context).item()
        val dataBaseSummary = DataBase.getDataBase(context).summary()

        dataBaseItem.deleteAllItems(
            month = month,
            year = year
        )

        dataBaseSummary.deleteSummaryByMonthAndYear(
            month = month,
            year = year
        )

        success()

        Toast.makeText(context, context.getString(R.string.toast_delete_summary), Toast.LENGTH_LONG).show()

    }

    fun returnValueOrStatus(
        context: Context,
        value: Double
    ): String {

        return if (value > 0.0) {
            String.format("%.2f", value)
        } else {
            context.getString(R.string.summary_pay)
        }
    }

    fun revenueLess(despesa: Double): String {

        viewModelScope.launch {
            String.format("%.2f", (state.dataSummary.first()?.revenue?.toDouble() ?: 0.0) - despesa)
        }

        return "0.0"

    }


}