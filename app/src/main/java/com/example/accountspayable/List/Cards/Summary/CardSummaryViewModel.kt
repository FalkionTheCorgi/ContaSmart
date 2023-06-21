package com.example.accountspayable.List.Cards.Summary

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.accountspayable.Data.GlobalVariables
import com.example.accountspayable.R
import com.example.accountspayable.Room.Data.DataSummary
import com.example.accountspayable.Room.Data.MonthYear
import com.example.accountspayable.Room.DataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.math.ceil
import kotlin.math.roundToLong

class CardSummaryViewModel(
    context: Context
) : ViewModel() {

    val state = CardSummaryState(
        context = context
    )

    init {
        getSummary(context)
        updateAllValuesOfPerson(context)
        updateExpenditure(context)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getSummary(context: Context) {

        viewModelScope.launch {
            GlobalVariables.monthSelected.combine(GlobalVariables.yearSelected) { month, year ->
                month to year
            }.flatMapLatest { (month, year) ->
                DataBase.getDataBase(context).summary().getASummaryByMonthAndYear(month!!, year!!)
            }.collect { summary ->
                state.dataSummary.value = summary
            }
        }

    }

    fun updateAllValuesOfPerson(context: Context) {


        viewModelScope.launch {
            GlobalVariables.monthSelected.combine(GlobalVariables.yearSelected) { month, year ->
                month to year
            }.flatMapLatest { (month, year) ->
                DataBase.getDataBase(context).item().getAllItemsByPersonChecked(
                    month = month!!,
                    year = year!!,
                    checkedPerson1 = false,
                    person1 = state.dataSummary.value?.person1 ?: "",
                    checkedPerson2 = false,
                    person2 = state.dataSummary.value?.person2 ?: "",
                    checkedPerson3 = false,
                    person3 = state.dataSummary.value?.person3 ?: "",
                    checkedPerson4 = false,
                    person4 = state.dataSummary.value?.person4 ?: ""
                )
            }.collect { list ->
                state.priceOfPerson1.value = 0.0
                state.priceOfPerson2.value = 0.0
                state.priceOfPerson3.value = 0.0
                state.priceOfPerson4.value = 0.0

                list.forEach {item ->

                    if(!item.checkedPerson1 && item.person1.isNotEmpty()) {
                        state.priceOfPerson1.value += ((item.priceOfPerson * 100.0).roundToLong() / 100.0)
                    }
                    if(!item.checkedPerson2 && item.person2.isNotEmpty()) {
                        state.priceOfPerson2.value += ((item.priceOfPerson * 100.0).roundToLong() / 100.0)
                    }
                    if(!item.checkedPerson3 && item.person3.isNotEmpty()) {
                        state.priceOfPerson3.value += ((item.priceOfPerson * 100.0).roundToLong() / 100.0)
                    }
                    if(!item.checkedPerson4 && item.person4.isNotEmpty()) {
                        state.priceOfPerson4.value += ((item.priceOfPerson * 100.0).roundToLong() / 100.0)
                    }

                }
            }
        }

    }

    fun updateExpenditure(context: Context){

        viewModelScope.launch {

            DataBase.getDataBase(context).item().getAllItems()
                .flowOn(Dispatchers.IO)
                .catch { }
                .collect{ items ->
                    state.expenditure.value = 0.0
                    items.forEach {item ->

                        state.expenditure.value += item.price

                    }

                }

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


        return String.format("%.2f", (state.dataSummary.value?.revenue?.toDouble() ?: 0.0) - despesa)


    }


}