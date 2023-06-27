package com.example.accountspayable.Data

import android.content.Context
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import java.util.*
import kotlin.concurrent.schedule
import kotlin.math.roundToLong
import com.example.accountspayable.R

fun returnRevenueToExpenditure(): String {

    var aux = 0.0

    listItems.forEach {
        aux += it.price
    }

    return String.format("%.2f",SUMMARY_CREATED_TODAY.revenue - aux)

}

fun returnSummaryValueByPerson(
    context: Context,
    p1: Boolean = false,
    p2: Boolean = false,
    p3: Boolean = false,
    p4: Boolean = false
): String {

    var priceOfPerson1 = 0.0
    var priceOfPerson2 = 0.0
    var priceOfPerson3 = 0.0
    var priceOfPerson4 = 0.0

    listItems.forEach { item ->

        if(!item.checkedPerson1 && item.person1.isNotEmpty()) {
            priceOfPerson1 += ((item.priceOfPerson * 100.0).roundToLong() / 100.0)
        }
        if(!item.checkedPerson2 && item.person2.isNotEmpty())  {
            priceOfPerson2 += ((item.priceOfPerson * 100.0).roundToLong() / 100.0)
        }
        if(!item.checkedPerson3 && item.person3.isNotEmpty())  {
            priceOfPerson3 += ((item.priceOfPerson * 100.0).roundToLong() / 100.0)
        }
        if(!item.checkedPerson4 && item.person4.isNotEmpty())  {
            priceOfPerson4 += ((item.priceOfPerson * 100.0).roundToLong() / 100.0)
        }

    }

    return if (p1){
        if (priceOfPerson1 == 0.0) { context.getString(R.string.summary_pay) } else { String.format("%.2f",priceOfPerson1) }
    } else if (p2){
        if (priceOfPerson2 == 0.0) { context.getString(R.string.summary_pay) } else { String.format("%.2f",priceOfPerson2) }
    } else if (p3) {
        if (priceOfPerson3 == 0.0) { context.getString(R.string.summary_pay) } else { String.format("%.2f",priceOfPerson3) }
    }else if (p4){
        if (priceOfPerson4 == 0.0) { context.getString(R.string.summary_pay) } else { String.format("%.2f",priceOfPerson4) }
    } else {
        if (priceOfPerson1 == 0.0) { context.getString(R.string.summary_pay) } else { String.format("%.2f",priceOfPerson1) }
    }

}

fun ComposeContentTestRule.waitUntilTimeout(
    timeoutMillis: Long
) {
    AsyncTimer.start(timeoutMillis)
    this.waitUntil(
        condition = { AsyncTimer.expired },
        timeoutMillis = timeoutMillis + 1000
    )
}

object AsyncTimer {
    var expired = false
    fun start(delay: Long = 1000) {
        expired = false
        Timer().schedule(delay) {
            expired = true
        }
    }
}