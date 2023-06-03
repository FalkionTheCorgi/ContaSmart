package com.example.accountspayable.List.Cards.Item

import android.content.Context
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

    fun sumQuantityOfPeople(
        p1: String,
        p2: String,
        p3: String,
        p4: String
    ): Int{

        var sum = 0

        if(p1.isNotEmpty())
            sum += 1
        if(p2.isNotEmpty())
            sum += 1
        if(p3.isNotEmpty())
            sum += 1
        if(p4.isNotEmpty())
            sum += 1

        return sum


    }


}