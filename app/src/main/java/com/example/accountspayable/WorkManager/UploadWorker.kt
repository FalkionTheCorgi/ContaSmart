package com.example.accountspayable.WorkManager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.accountspayable.GoogleDrive.GoogleDriveService
import com.example.accountspayable.Room.BackupDataBase
import org.koin.core.parameter.parametersOf

class UploadDataBase(appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {

    val googleDrive : GoogleDriveService = GoogleDriveService(appContext)

    override fun doWork(): Result {

        /*backupDataBase.exportDatabase(
            context = applicationContext
        )*/

        googleDrive.uploadFile(applicationContext)

        return Result.success()

    }


}