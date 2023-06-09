package com.example.accountspayable.BottomSheet.Calendar

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.accountspayable.getTodayDate


class BottomSheetCalendarState(
    application: Application
) {

    val months = mutableStateListOf(
        "Janeiro",
        "Fevereiro",
        "Março",
        "Abril",
        "Maio",
        "Junho",
        "Julho",
        "Agosto",
        "Setembro",
        "Outubro",
        "Novembro",
        "Dezembro"
    )
    val years = mutableStateListOf(
        "2023",
        "2024",
        "2025",
        "2026",
        "2027",
        "2028",
        "2029",
        "2030",
        "2031",
        "2032",
        "2033"
    )

    var monthSelected = mutableStateOf(getTodayDate()?.monthValue)
    var yearSelected = mutableStateOf(getTodayDate()?.year)

}