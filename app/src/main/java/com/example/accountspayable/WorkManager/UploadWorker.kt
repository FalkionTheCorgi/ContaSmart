package com.example.accountspayable.WorkManager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.accountspayable.Room.BackupDataBase

class ExportDataBase(appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {

    val backupDataBase : BackupDataBase = BackupDataBase()

    override fun doWork(): Result {

        backupDataBase.exportDatabase(
            context = applicationContext
        )

        return Result.success()

    }


}