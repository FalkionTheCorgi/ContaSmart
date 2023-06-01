package com.example.accountspayable.BottomSheet.Calendar

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.accountspayable.getTodayDate

class BottomSheetCalendarViewModel: ViewModel() {

    val state = BottomSheetCalendarState()



    fun returnMonth(str: String): Int {

        return when(str){

            "Janeiro" -> 1
            "Fevereiro" -> 2
            "Março" -> 3
            "Abril" -> 4
            "Maio" -> 5
            "Junho" -> 6
            "Julho" -> 7
            "Agosto" -> 8
            "Setembro" -> 9
            "Outubro" -> 10
            "Novembro" -> 11
            "Dezembro" -> 12
            else -> 1

        }


    }

}