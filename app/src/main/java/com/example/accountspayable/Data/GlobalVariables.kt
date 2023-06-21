package com.example.accountspayable.Data

import androidx.compose.runtime.mutableStateOf
import com.example.accountspayable.getTodayDate
import kotlinx.coroutines.flow.MutableStateFlow

object GlobalVariables{

    var monthSelected = MutableStateFlow(getTodayDate()?.monthValue)
    var yearSelected = MutableStateFlow(getTodayDate()?.year)


}