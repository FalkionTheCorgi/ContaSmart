package com.example.accountspayable.WorkManager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.accountspayable.DataStore.DataStore
import com.example.accountspayable.GoogleDrive.GoogleDriveService
import com.example.accountspayable.Room.BackupDataBase
import kotlinx.coroutines.flow.first
import org.koin.core.parameter.parametersOf

class UploadDataBase(appContext: Context, workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams) {

    val googleDrive : GoogleDriveService = GoogleDriveService(appContext)
    val dataStore: DataStore = DataStore(appContext)

    override suspend fun doWork(): Result {

            /*backupDataBase.exportDatabase(
                context = applicationContext
            )*/
            if(dataStore.getBackupMode.first()) {
                googleDrive.uploadFile(applicationContext)
            }

            return Result.success()

    }


}