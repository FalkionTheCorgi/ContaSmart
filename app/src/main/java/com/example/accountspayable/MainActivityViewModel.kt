package com.example.accountspayable

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.accountspayable.Room.Data.DataItem
import com.example.accountspayable.Room.Data.DataSummary
import com.example.accountspayable.Room.Data.MonthYear
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivityViewModel: ViewModel(){


    var bottomSheetType = mutableStateOf (BottomSheetTypes.NONE)

    var editCardObj = mutableStateOf(
        DataItem(
            id = 0,
            name = "",
            valor = 0.0,
            descricao = "",
            icon = "Light",
            person1 = "",
            check1 = false,
            person2 = "",
            check2 = false,
            person3 = "",
            check3 = false,
            person4 = "",
            check4 = false,
            mYear = MonthYear(
                month = 0,
                year = 0,
                vencimento = 0
            )
        )
    )

    var editCardSummary = mutableStateOf(
        DataSummary(
            id = 0,
            revenue = 0f,
            person1 = "",
            person2 = "",
            person3 = "",
            person4 = "",
            mYear = MonthYear(
                month = 0,
                year = 0,
                vencimento = 0
            )
        )
    )

    var updateSummaryPerson1 = mutableStateOf(false)
    var updateSummaryPerson2 = mutableStateOf(false)
    var updateSummaryPerson3 = mutableStateOf(false)
    var updateSummaryPerson4 = mutableStateOf(false)
    var updateSummaryAllPerson = mutableStateOf(false)
    var resetCardSummary = mutableStateOf(false)
    var updateBottomSheet = mutableStateOf(false)
    var monthSelected = mutableStateOf(getTodayDate()?.monthValue)
    var yearSelected = mutableStateOf(getTodayDate()?.year)
    var openAlertCreateSummary = mutableStateOf(false)

    var darkMode = mutableStateOf(false)
    var backupData = mutableStateOf(false)
    var permissionStorage = mutableStateOf(false)
    val isLoading = mutableStateOf(true)



}

enum class BottomSheetTypes {

    ADD, EDIT, CALENDAR, SUMMARYADD, SUMMARYEDIT, SUGGESTION, DONATION, SETTINGS, NONE

}