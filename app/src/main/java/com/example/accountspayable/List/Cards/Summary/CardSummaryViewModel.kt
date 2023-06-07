package com.example.accountspayable.List.Cards.Summary

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.accountspayable.Room.Data.DataSummary
import com.example.accountspayable.Room.Data.MonthYear
import com.example.accountspayable.Room.DataBase
import kotlin.math.ceil

class CardSummaryViewModel : ViewModel() {

    val state = CardSummaryState()

    suspend fun onAppearCardSummary(
        context: Context,
        month: Int,
        year: Int
    ){

        state.dataSummary.clear()

        val summaryDao = DataBase.getDataBase(context).summary()

        val summary = summaryDao.getASummaryByMonthAndYear(
            month = month,
            year = year
        )

        if (summary.isNotEmpty()) {

            state.dataSummary.add(
                DataSummary(
                    id = summary[0].id,
                    revenue = summary[0].revenue,
                    person1 = summary[0].person1,
                    person2 = summary[0].person2,
                    person3 = summary[0].person3,
                    person4 = summary[0].person4,
                    mYear = MonthYear(
                        month = summary[0].month,
                        year = summary[0].year,
                        vencimento = 0
                    )
                )
            )

            updateAllValuesOfPerson(
                context = context,
                month = month,
                year = year
            )

        }

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
            person1 = if(state.dataSummary.isNotEmpty()) { state.dataSummary.first().person1 } else { "" }
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
            person2 = if(state.dataSummary.isNotEmpty()) { state.dataSummary.first().person2 } else { "" }
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
            person3 = if(state.dataSummary.isNotEmpty()) { state.dataSummary.first().person3 } else { "" }
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
            person4 = if(state.dataSummary.isNotEmpty()) { state.dataSummary.first().person4 } else { "" }
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

        Toast.makeText(context, "Sumário e itens deletados com sucesso!", Toast.LENGTH_LONG).show()

    }

    fun returnValueOrStatus(value: Double): String {

        return if (value > 0.0) {
            String.format("%.2f", value)
        } else {
            "Pago"
        }
    }

    fun revenueLess(despesa: Double): String {

        return String.format("%.2f", state.dataSummary.first().revenue.toDouble() - despesa)

    }


}