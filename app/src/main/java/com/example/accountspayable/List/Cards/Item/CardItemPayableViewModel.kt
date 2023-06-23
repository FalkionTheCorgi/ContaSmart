package com.example.accountspayable.List.Cards.Item

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.accountspayable.Room.DataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class CardItemPayableViewModel : ViewModel(){

    val state = CardItemPayableState()

    suspend fun checkBoxUpdateCard(
        context: Context,
        id: Int
    ){
        val dataBase = DataBase.getDataBase(context).item()

        dataBase.updateCheckBoxPeople(
            id = id,
            checkedPerson1 = state.checkBoxPerson1.value,
            checkedPerson2 = state.checkBoxPerson2.value,
            checkedPerson3 = state.checkBoxPerson3.value,
            checkedPerson4 = state.checkBoxPerson4.value
        )

    }

    fun fillCheckPerson(
        id: Int,
        context: Context
    ){
        viewModelScope.launch {
            DataBase.getDataBase(context).item().getItemById(id)
                .flowOn(Dispatchers.IO)
                .catch { }
                .collect { item ->
                    state.checkBoxPerson1.value = item?.checkedPerson1 ?: false
                    state.checkBoxPerson2.value = item?.checkedPerson2 ?: false
                    state.checkBoxPerson3.value = item?.checkedPerson3 ?: false
                    state.checkBoxPerson4.value = item?.checkedPerson4 ?: false
                }
        }
    }


}