package com.example.accountspayable.BottomSheet.Calendar


import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.accountspayable.R

class BottomSheetCalendarViewModel(
    application: Application
): ViewModel() {

    @SuppressLint("StaticFieldLeak")
    val context = application.applicationContext

    val state = BottomSheetCalendarState(
        application
    )



    fun returnMonth(str: String): Int {

        return when(str){

            context.getString(R.string.january) -> 1
            context.getString(R.string.february) -> 2
            context.getString(R.string.march) -> 3
            context.getString(R.string.april) -> 4
            context.getString(R.string.may) -> 5
            context.getString(R.string.june) -> 6
            context.getString(R.string.july) -> 7
            context.getString(R.string.august) -> 8
            context.getString(R.string.september) -> 9
            context.getString(R.string.october) -> 10
            context.getString(R.string.november) -> 11
            context.getString(R.string.december) -> 12
            else -> 1

        }


    }

}