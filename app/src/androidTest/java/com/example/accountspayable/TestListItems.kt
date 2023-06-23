package com.example.accountspayable

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.accountspayable.Data.ITEM_ENTITY_LESS_3DAYS
import com.example.accountspayable.Data.SUMMARY_CREATED_TODAY
import com.example.accountspayable.Room.DataBase
import com.example.accountspayable.Room.Summary.SummaryDao
import com.example.accountspayable.ui.theme.AccountsPayableTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class TestListItems {

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()
    private lateinit var navController: NavHostController



    @Before
    fun setup(){

        runBlocking {
            DataBase.getDataBase(context).summary().insertSummary(SUMMARY_CREATED_TODAY)
            DataBase.getDataBase(context).item().insertItem(ITEM_ENTITY_LESS_3DAYS)
        }

    }

    @Test
    @Throws(Exception::class)
    fun testUploadRoomDatabase(): Unit = runBlocking{

       with(composeRule){

       }

        delay(10000)

    }

    @After
    fun cleanup() {

    }

}