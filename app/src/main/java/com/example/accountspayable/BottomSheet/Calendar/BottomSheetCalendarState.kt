package com.example.accountspayable.BottomSheet.Calendar

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.accountspayable.R
import com.example.accountspayable.getTodayDate


class BottomSheetCalendarState(
    application: Application
) {

    val context = application.applicationContext

    val months = mutableStateListOf(
        context.getString(R.string.january),
        context.getString(R.string.february),
        context.getString(R.string.march),
        context.getString(R.string.april),
        context.getString(R.string.may),
        context.getString(R.string.june),
        context.getString(R.string.july),
        context.getString(R.string.august),
        context.getString(R.string.september),
        context.getString(R.string.october),
        context.getString(R.string.november),
        context.getString(R.string.december)
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