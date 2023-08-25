package com.example.accountspayable

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager.*
import com.example.accountspayable.DataStore.DataStore
import com.example.accountspayable.WorkManager.UploadDataBase
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WorkManagerUpload {

    @Before
    fun writeItemDeadline() = runBlocking{

        val context = ApplicationProvider.getApplicationContext<Context>()

        val dataStore = DataStore(context)

        dataStore.saveBackupMode(true)

    }

    @Test
    @Throws(Exception::class)
    fun testUploadRoomDatabase(): Unit = runBlocking{

        val request = OneTimeWorkRequestBuilder<UploadDataBase>()
            .build()

        val workManager = getInstance(
            InstrumentationRegistry
                .getInstrumentation()
                .targetContext
        )

        workManager.enqueue(request)

        delay(30000)

    }

}