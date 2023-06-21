package com.example.accountspayable.BottomSheet.Item

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.accountspayable.R
import com.example.accountspayable.Room.Data.DataSummary
import com.example.accountspayable.Room.Data.MonthYear
import com.example.accountspayable.Room.DataBase
import com.example.accountspayable.Room.Item.ItemEntity
import com.example.accountspayable.getDaysInMonth

class BottomSheetViewModel(
    application: Application
) : ViewModel() {

    val state = BottomSheetState(
        application
    )

    suspend fun addItem(
        context: Context,
        month: Int,
        year: Int,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ){
        state.progressBtn.value = true
        state.textButton.value = context.getString(R.string.btn_wait)
        verifyFieldItemName(state.itemName.value)
        verifyFieldItemValue(state.itemValue.value)
        verifyFieldItemDeadline(state.itemDeadline.value)


        if (
            verifyItem()
        ) {

            val dataBase = DataBase.getDataBase(context).item()

            dataBase.insertItem(
                ItemEntity(
                    itemName = state.itemName.value,
                    price = state.itemValue.value.toDouble(),
                    description = state.description.value,
                    icon = state.iconOption.value,
                    vencimento = if(state.itemDeadline.value.isNotEmpty()){state.itemDeadline.value.toInt()}else{0},
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
                    priceOfPerson = (state.itemValue.value.toDouble() / returnDiv() )
                )
            )

            state.progressBtn.value = false
            state.textButton.value = context.getString(R.string.btn_save)

            Toast.makeText(
                context,
                context.getString(R.string.toast_data_save_success),
                Toast.LENGTH_LONG
            ).show()

            onSuccess()

        } else {

            Toast.makeText(
                context,
                context.getString(R.string.toast_data_check_red_fields),
                Toast.LENGTH_LONG
            ).show()

            state.progressBtn.value = false
            state.textButton.value = context.getString(R.string.btn_save)
            onFailure()
        }


    }

    suspend fun editItem(
        context: Context,
        checkBefore1: Boolean,
        checkBefore2: Boolean,
        checkBefore3: Boolean,
        checkBefore4: Boolean,
        id: Int,
        onSuccess: () -> Unit,
        onFailure: () -> Unit

    ){
        state.progressBtn.value = true
        state.textButton.value = context.getString(R.string.btn_wait)
        verifyFieldItemName(state.itemName.value)
        verifyFieldItemValue(state.itemValue.value)
        verifyFieldItemDeadline(state.itemDeadline.value)

        if (
            verifyItem()
        ) {

            val dataBase = DataBase.getDataBase(context).item()

            dataBase.updateItem(
                id = id,
                name = state.itemName.value,
                price = state.itemValue.value.toDouble(),
                description = state.description.value,
                icon = state.iconOption.value,
                vencimento = if(state.itemDeadline.value.isNotEmpty()){state.itemDeadline.value.toInt()}else{0},
                person1 = if (state.checkPerson1.value) { state.person1.value } else { "" },
                person2 = if (state.checkPerson2.value) { state.person2.value } else { "" },
                person3 = if (state.checkPerson3.value) { state.person3.value } else { "" },
                person4 = if (state.checkPerson4.value) { state.person4.value } else { "" },
                priceOfPerson = (state.itemValue.value.toDouble()/returnDiv()),
                checkedPerson1 = verifyCheckPerson(state.person1.value, checkBefore1, state.checkPerson1.value),
                checkedPerson2 = verifyCheckPerson(state.person2.value, checkBefore2, state.checkPerson2.value),
                checkedPerson3 = verifyCheckPerson(state.person3.value, checkBefore3, state.checkPerson3.value),
                checkedPerson4 = verifyCheckPerson(state.person4.value, checkBefore4, state.checkPerson4.value)
            )

            state.progressBtn.value = false
            state.textButton.value = context.getString(R.string.btn_edit)

            Toast.makeText(
                context,
                context.getString(R.string.toast_data_edit_success),
                Toast.LENGTH_LONG
            ).show()

            onSuccess()

        } else {

            Toast.makeText(
                context,
                context.getString(R.string.toast_data_check_red_fields),
                Toast.LENGTH_LONG
            ).show()

            state.progressBtn.value = false
            state.textButton.value = context.getString(R.string.btn_edit)

            onFailure()

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
                    month = summary?.month ?: 1,
                    vencimento = 0
                )
            )
        )

    }

    fun onAppearBtmSheet(
        context: Context,
        itemName: String,
        price: String,
        description: String,
        icon: String,
        vencimento: String,
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
        state.itemDeadline.value = vencimento
        state.textButton.value = if (isEdit) { context.getString(R.string.btn_edit) } else { context.getString(R.string.btn_save) }
        state.person1.value = person1
        state.person2.value = person2
        state.person3.value = person3
        state.person4.value = person4
        state.checkPerson1.value = checkedPerson1
        state.checkPerson2.value = checkedPerson2
        state.checkPerson3.value = checkedPerson3
        state.checkPerson4.value = checkedPerson4
        state.redFieldItemDeadline.value = false
        state.redFieldItemName.value = false
        state.redFieldItemValue.value = false

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

    fun verifyItem(): Boolean{

        if (state.itemName.value.isNotEmpty() &&
            state.itemValue.value.isNotEmpty()){

            if (state.itemDeadline.value.isNotEmpty() && getDaysInMonth() < state.itemDeadline.value.toInt()) {

                return false

            } else if(state.itemDeadline.value.isNotEmpty() && getDaysInMonth() >= state.itemDeadline.value.toInt()) {

                return true

            } else if (state.itemDeadline.value.isEmpty()) {

                return true

            } else {

                return true

            }

        } else {

            return false

        }

    }

    fun verifyFieldItemName(
        str: String
    ){

        state.redFieldItemName.value = str.isEmpty()

    }

    fun verifyFieldItemValue(
        str: String
    ){

        state.redFieldItemValue.value = str.isEmpty()

    }

    fun verifyFieldItemDeadline(
        str: String
    ){

        if (str.isNotEmpty() && str.toInt() == 0){

            state.redFieldItemDeadline.value = true

        } else state.redFieldItemDeadline.value =
            !((str.isEmpty()) || (str.isNotEmpty() && getDaysInMonth() >= str.toInt()))


    }




}