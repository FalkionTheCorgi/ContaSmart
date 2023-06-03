package com.example.accountspayable.WorkManager

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.accountspayable.NOTIFICATION_CHANNEL_ID
import com.example.accountspayable.NOTIFICATION_ID
import com.example.accountspayable.R
import com.example.accountspayable.Room.DataBase
import com.example.accountspayable.getTodayDate

class NotificationDeadline(appContext: Context, workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams) {



    override suspend fun doWork(): Result {

        val items = DataBase.getDataBase(applicationContext).item()

        val list = items.getAllItemsByDeadlineTomorrow(
            vencimento = ((getTodayDate()?.dayOfMonth ?: -1 ) + 1)
        )

        if (list.isNotEmpty()) {

            val builder = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Aviso")
                .setContentText("Você tem contas próximas da data de vencimento")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(applicationContext)){
                if (ActivityCompat.checkSelfPermission(
                        applicationContext,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    notify(NOTIFICATION_ID, builder.build())
                }

            }

        }

        return Result.success()

    }


}