package com.example.accountspayable.WorkManager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.accountspayable.Room.BackupDataBase

class ExportDataBase(appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {

    val backupDataBase : BackupDataBase = BackupDataBase()

    companion object{
        /**
         * Constantes com alguns dados da configuração
         * inicial de WorkManager.
         */
        const val NAME = "sync_local_database"
        const val REPEAT_INTERVAL : Long = 60
    }

    override fun doWork(): Result {

        backupDataBase.exportDatabase(
            context = applicationContext
        )

        return Result.success()

    }


}