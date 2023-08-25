package com.example.accountspayable.List

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.accountspayable.Data.GlobalVariables
import com.example.accountspayable.R
import com.example.accountspayable.Room.Data.DataItem
import com.example.accountspayable.Room.Data.MonthYear
import com.example.accountspayable.Room.DataBase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ListAccountsPayableViewModel(
    context: Context
): ViewModel(){

    val state = ListAccounstPayableState()
    init {
        getItems(context)
    }


    fun getItems(
        context: Context
    ){

        viewModelScope.launch {
            GlobalVariables.monthSelected.combine(GlobalVariables.yearSelected) { month, year ->
                month to year
            }.flatMapLatest { (month, year) ->
                DataBase.getDataBase(context).item().getAllItemsByMonthAndYear(month!!, year!!)
            }.collect { items ->
                state.dataItem.value = items
            }
        }

    }

    suspend fun deleteItem(
        context: Context,
        id : Int
    ){
        val dataBase = DataBase.getDataBase(context).item()

        dataBase.deleteById(
            id = id
        )

        viewModelScope.launch {
            Toast.makeText(context, context.getText(R.string.removed_success), Toast.LENGTH_LONG).show()
        }


    }


}