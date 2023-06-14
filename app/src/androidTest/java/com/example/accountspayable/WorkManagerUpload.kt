package com.example.accountspayable

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager.*
import com.example.accountspayable.WorkManager.UploadDataBase
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WorkManagerUpload {

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