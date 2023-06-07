package com.example.accountspayable.List.Cards.Item

import android.content.Context
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.accountspayable.Room.Data.DataSummary
import com.example.accountspayable.Room.Data.MonthYear
import com.example.accountspayable.Room.DataBase
import com.example.accountspayable.getTodayDate

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
        check1: Boolean,
        check2: Boolean,
        check3: Boolean,
        check4: Boolean
    ){

        state.checkBoxPerson1.value = check1
        state.checkBoxPerson2.value = check2
        state.checkBoxPerson3.value = check3
        state.checkBoxPerson4.value = check4

    }

    fun getSizeIcon(icon: String): Dp {

        return when(icon) {

            "Water" -> {
                24.dp
            }

            "Light" -> {
                32.dp
            }

            "Market" -> {
                32.dp
            }

            "Router" -> {
                32.dp
            }

            "Card" -> {
                32.dp
            }

            "Restaurant" -> {
                32.dp
            }

            "House" -> {
                32.dp
            }

            "Game" -> {
                32.dp
            }

            "Phone" -> {
                32.dp
            }

            "Other" -> {
                32.dp
            }

            else -> {
                32.dp
            }
        }
    }

}