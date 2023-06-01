package com.example.accountspayable.List

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.accountspayable.BottomSheet.Item.BottomSheetState
import com.example.accountspayable.BottomSheet.Summary.BottomSheetSumaryState
import com.example.accountspayable.Room.Data.DataItem
import com.example.accountspayable.Room.Data.DataSummary
import com.example.accountspayable.Room.Data.MonthYear
import com.example.accountspayable.Room.DataBase
import com.example.accountspayable.Room.Item.ItemEntity
import com.example.accountspayable.Room.Summary.SummaryEntity
import com.example.accountspayable.getTodayDate

class ListAccountsPayableViewModel: ViewModel(){

    val state = ListAccounstPayableState()

    suspend fun onAppearScreen(
        context: Context,
        month: Int,
        year: Int
    ){

        state.dataItem.clear()

        val itemDao = DataBase.getDataBase(context).item()

        val items = itemDao.getAllItemsByMonthAndYear(
            month = month,
            year = year
        )

        items.forEach { item ->

            state.dataItem.add(
                DataItem(
                    id = item.id,
                    name = item.itemName,
                    valor = item.price,
                    descricao = item.description,
                    icon = item.icon,
                    person1 = item.person1,
                    check1 = item.checkedPerson1,
                    person2 = item.person2,
                    check2 = item.checkedPerson2,
                    person3 = item.person3,
                    check3 = item.checkedPerson3,
                    person4 = item.person4,
                    check4 = item.checkedPerson4,
                    mYear = MonthYear(
                        month = item.month,
                        year = item.year
                    )
                )
            )

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

        state.dataItem.removeIf { id == it?.id }

        Toast.makeText(context, "Removido com sucesso", Toast.LENGTH_LONG).show()
    }

    fun sumExpenditure(): String{

        var sum = 0f

        state.dataItem.forEach{

            it?.valor.let {
                sum += it ?: 0f
            }


        }

        return sum.toString()

    }





    fun getMonthOfCard(
        id: Int
    ): Int {

        val card = state.dataItem.filter {
            it?.id == id
        }

        return card.first()?.mYear?.month ?: -1

    }

    fun getYearOfCard(
        id: Int
    ): Int {

        val card = state.dataItem.filter {
            it?.id == id
        }

        return card.first()?.mYear?.year ?: -1

    }

    fun updateCheckPersonList(
        id: Int,
        check1: Boolean,
        check2: Boolean,
        check3: Boolean,
        check4: Boolean
    ) {

        val index = state.dataItem.indexOfFirst { it?.id == id }

        state.dataItem[index]?.check1 = check1
        state.dataItem[index]?.check2 = check2
        state.dataItem[index]?.check3 = check3
        state.dataItem[index]?.check4 = check4

    }


}