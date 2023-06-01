package com.example.accountspayable.BottomSheet.Summary

import android.content.Context
import android.content.LocusId
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.accountspayable.Room.DataBase
import com.example.accountspayable.Room.Summary.SummaryEntity
import com.example.accountspayable.getTodayDate

class BottomSheetSummaryViewModel: ViewModel() {

    val state = BottomSheetSumaryState()


    fun onAppearBtmSheetSummary(
        isEdit : Boolean,
        revenue: String,
        person1: String = "",
        person2: String = "",
        person3: String = "",
        person4: String = ""
    ){

        state.revenue.value = revenue
        state.person1.value = person1
        state.person2.value = person2
        state.person3.value = person3
        state.person4.value = person4

        if (isEdit) {

            state.textButton.value = "EDITAR"

        }

    }

    suspend fun addSummary(
        context: Context,
        month: Int,
        year: Int,
        onSuccess: () -> Unit
    ){

        val dataBase = DataBase.getDataBase(context).summary()
        state.progressBtn.value = true
        state.textButton.value = "AGUARDAR"

        if (state.revenue.value.isNotEmpty()){

            dataBase.insertSummary(
                SummaryEntity(
                    revenue = state.revenue.value.toFloat(),
                    month = month,
                    year = year,
                    person1 = state.person1.value,
                    person2 = state.person2.value,
                    person3= state.person3.value,
                    person4 = state.person4.value
                )
            )

            state.progressBtn.value = false
            state.textButton.value = "SALVAR"

            Toast.makeText(
                context,
                "Dados salvos com sucesso.",
                Toast.LENGTH_LONG
            ).show()

            onSuccess()

        } else {

            Toast.makeText(
                context,
                "Por favor preencha o campo de receita",
                Toast.LENGTH_LONG
            ).show()

            state.progressBtn.value = false
            state.textButton.value = "SALVAR"

        }

    }

    suspend fun editSummary(
        id: Int,
        context: Context,
        onSuccess: () -> Unit
    ){

        val dataBase = DataBase.getDataBase(context).summary()
        val dataBaseItem = DataBase.getDataBase(context).item()

        state.progressBtn.value = true
        state.textButton.value = "AGUARDAR"

        if (state.revenue.value.isNotEmpty()){

            dataBase.updateSummary(
                id = id,
                revenue = state.revenue.value.toFloat(),
                person1 = state.person1.value,
                person2 = state.person2.value,
                person3 = state.person3.value,
                person4 = state.person4.value
            )

            val items = dataBaseItem.getAllItems()

            items.forEach {  item ->

               dataBaseItem.updateItem(
                   id = item.id,
                   name = item.itemName,
                   price = item.price,
                   description = item.description,
                   icon = item.icon,
                   person1 = if(item.person1.isNotEmpty()){ state.person1.value } else { "" },
                   person2 = if(item.person2.isNotEmpty()){ state.person2.value } else { "" },
                   person3 = if(item.person3.isNotEmpty()){ state.person3.value } else { "" },
                   person4 = if(item.person4.isNotEmpty()){ state.person4.value } else { "" },
                   priceOfPerson = item.priceOfPerson,
                   checkedPerson1 = item.checkedPerson1,
                   checkedPerson2 = item.checkedPerson2,
                   checkedPerson3 = item.checkedPerson3,
                   checkedPerson4 = item.checkedPerson4
               )

            }

            state.progressBtn.value = false
            state.textButton.value = "EDITAR"

            Toast.makeText(
                context,
                "Dados editados com sucesso.",
                Toast.LENGTH_LONG
            ).show()

            onSuccess()

        } else {

            Toast.makeText(
                context,
                "Por favor preencha o campo de receita",
                Toast.LENGTH_LONG
            ).show()

            state.progressBtn.value = false
            state.textButton.value = "EDITAR"

        }

    }

}