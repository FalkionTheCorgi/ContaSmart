package com.example.accountspayable.Data

import androidx.compose.runtime.mutableStateOf
import com.example.accountspayable.getTodayDate

object GlobalVariables{

    var monthSelected = mutableStateOf(getTodayDate()?.monthValue)
    var yearSelected = mutableStateOf(getTodayDate()?.year)


}