package com.example.accountspayable.BottomSheet.Item

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.accountspayable.Room.Data.DataSummary
import com.example.accountspayable.Room.Data.MonthYear
import com.example.accountspayable.Room.DataBase
import com.example.accountspayable.Room.Item.ItemEntity
import com.example.accountspayable.getTodayDate

class BottomSheetViewModel : ViewModel() {

    val state = BottomSheetState()

    suspend fun addItem(
        context: Context,
        month: Int,
        year: Int
    ){
        state.progressBtn.value = true
        state.textButton.value = "AGUARDAR"


        if (
            state.itemName.value.isNotEmpty() &&
            state.itemValue.value.isNotEmpty()
        ) {

            val dataBase = DataBase.getDataBase(context).item()

            dataBase.insertItem(
                ItemEntity(
                    itemName = state.itemName.value,
                    price = state.itemValue.value.toFloat(),
                    description = state.description.value,
                    icon = state.iconOption.value,
                    month = month,
                    year = year,
                    person1 = if (state.checkPerson1.value) { state.person1.value } else { "" },
                    person2 = if (state.checkPerson2.value) { state.person2.value } else { "" },
                    person3 = if (state.checkPerson3.value) { state.person3.value } else { "" },
                    person4 = if (state.checkPerson4.value) { state.person4.value } else { "" },
                    checkedPerson1 = false,
                    checkedPerson2 = false,
                    checkedPerson3 = false,
                    checkedPerson4 = false,
                    priceOfPerson = (state.itemValue.value.toFloat() / returnDiv() )
                )
            )

            state.progressBtn.value = false
            state.textButton.value = "SALVAR"

            Toast.makeText(
                context,
                "Dados salvos com sucesso.",
                Toast.LENGTH_LONG
            ).show()

        } else {

            Toast.makeText(
                context,
                "Por favor preencha o campo de Item e preço",
                Toast.LENGTH_LONG
            ).show()

            state.progressBtn.value = false
            state.textButton.value = "SALVAR"
        }


    }

    suspend fun editItem(
        context: Context,
        checkBefore1: Boolean,
        checkBefore2: Boolean,
        checkBefore3: Boolean,
        checkBefore4: Boolean,
        id: Int
    ){

        if (
            state.itemName.value.isNotEmpty() &&
            state.itemValue.value.isNotEmpty()
        ) {

            val dataBase = DataBase.getDataBase(context).item()

            dataBase.updateItem(
                id = id,
                name = state.itemName.value,
                price = state.itemValue.value.toFloat(),
                description = state.description.value,
                icon = state.iconOption.value,
                person1 = if (state.checkPerson1.value) { state.person1.value } else { "" },
                person2 = if (state.checkPerson2.value) { state.person2.value } else { "" },
                person3 = if (state.checkPerson3.value) { state.person3.value } else { "" },
                person4 = if (state.checkPerson4.value) { state.person4.value } else { "" },
                priceOfPerson = (state.itemValue.value.toFloat()/returnDiv()),
                checkedPerson1 = verifyCheckPerson(state.person1.value, checkBefore1, state.checkPerson1.value),
                checkedPerson2 = verifyCheckPerson(state.person2.value, checkBefore2, state.checkPerson2.value),
                checkedPerson3 = verifyCheckPerson(state.person3.value, checkBefore3, state.checkPerson3.value),
                checkedPerson4 = verifyCheckPerson(state.person4.value, checkBefore4, state.checkPerson4.value)
            )

            state.progressBtn.value = false
            state.textButton.value = "EDITAR"

            Toast.makeText(
                context,
                "Dados editados com sucesso.",
                Toast.LENGTH_LONG
            ).show()

        } else {

            Toast.makeText(
                context,
                "Por favor preencha o campo de Item e preço",
                Toast.LENGTH_LONG
            ).show()

            state.progressBtn.value = false
            state.textButton.value = "EDITAR"

        }

    }

    fun verifyCheckPerson(
        person: String,
        check: Boolean,
        checkState: Boolean
    ): Boolean{

        return person.isNotEmpty() && check && !checkState

    }

    suspend fun getSummary(
        id: Int,
        context: Context
    ){

        val dataBase = DataBase.getDataBase(context).summary()

        val summary = dataBase.getSummaryById(
            id = id
        )

        state.dataSummary.add(
            DataSummary(
                id = summary?.id ?: 0,
                revenue = summary?.revenue ?: 0f,
                person1 = summary?.person1 ?: "",
                person2 = summary?.person2 ?: "",
                person3 = summary?.person3 ?: "",
                person4 = summary?.person4 ?: "",
                mYear = MonthYear(
                    year = summary?.year ?: 2023,
                    month = summary?.month ?: 1
                )
            )
        )

    }

    fun onAppearBtmSheet(
        itemName: String,
        price: String,
        description: String,
        icon: String,
        person1 : String,
        person2 : String,
        person3 : String,
        person4 : String,
        checkedPerson1 : Boolean,
        checkedPerson2 : Boolean,
        checkedPerson3 : Boolean,
        checkedPerson4 : Boolean,
        isEdit: Boolean
    ){

        state.iconOption.value = icon
        state.itemName.value = itemName
        state.description.value = description
        state.itemValue.value = price
        state.textButton.value = if (isEdit) { "EDITAR" } else { "SALVAR" }
        state.person1.value = person1
        state.person2.value = person2
        state.person3.value = person3
        state.person4.value = person4
        state.checkPerson1.value = checkedPerson1
        state.checkPerson2.value = checkedPerson2
        state.checkPerson3.value = checkedPerson3
        state.checkPerson4.value = checkedPerson4

    }

    fun returnDiv(): Int{

        var aux = 1

        if(state.checkPerson1.value)
            aux += 1
        if(state.checkPerson2.value)
            aux += 1
        if(state.checkPerson3.value)
            aux += 1
        if(state.checkPerson4.value)
            aux += 1

        return aux
    }


}