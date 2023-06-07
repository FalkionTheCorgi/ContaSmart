package com.example.accountspayable

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.Configuration
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.example.accountspayable.Data.ITEM_ENTITY_LESS_3DAYS
import com.example.accountspayable.Room.DataBase
import com.example.accountspayable.Room.Item.ItemDao
import com.example.accountspayable.Room.Item.ItemEntity
import com.example.accountspayable.WorkManager.NotificationDeadline
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private val testDispatcher = TestCoroutineScheduler()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun writeItemDeadline() = runBlocking{

        val context = ApplicationProvider.getApplicationContext<Context>()

        DataBase.getDataBase(context).item().insertItem(
            ITEM_ENTITY_LESS_3DAYS
        )

    }

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val config = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()

        // Initialize WorkManager for instrumentation tests.
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
    }

    @After
    fun deleteItem() = runBlocking {

        val context = ApplicationProvider.getApplicationContext<Context>()

        DataBase.getDataBase(context).item().deleteItem(
            ITEM_ENTITY_LESS_3DAYS
        )

    }

    @Test
    @Throws(Exception::class)
    fun testWorker(): Unit = runBlocking{

        val request = OneTimeWorkRequestBuilder<NotificationDeadline>()
            .build()

        val workManager = WorkManager.getInstance(
            InstrumentationRegistry
                .getInstrumentation()
                .targetContext
        )

        workManager.enqueue(request)

        delay(10000)

    }
}