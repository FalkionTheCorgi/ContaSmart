package com.example.accountspayable.WorkManager

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.accountspayable.*
import com.example.accountspayable.Room.DataBase

class NotificationDeadline(appContext: Context, workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams) {



    @SuppressLint("MissingPermission")
    override suspend fun doWork(): Result {

        val items = DataBase.getDataBase(applicationContext).item()

        val list = items.getAllItemsByDeadlineTomorrow(
            todayData = getTodayDate()?.dayOfMonth ?: -1,
            vencimento = ((getTodayDate()?.dayOfMonth ?: -1 ) + 3),
            month = getTodayDate()?.monthValue ?: -1,
            year = getTodayDate()?.year ?: 2023
        )

        if (list.isNotEmpty()) {

            createNotificationChannel()

            val intent = Intent(applicationContext, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE);

            val builder = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_cs)
                .setContentTitle("Aviso")
                .setContentText("Você tem contas próximas a data de vencimento")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            builder.setContentIntent(pendingIntent).setAutoCancel(true)

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

    fun createNotificationChannel(){

        val name = "Deadline"

        val nChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            name,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        nChannel.description = "Notificação usada para alertar ao usuário sobre data de vencimento"

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(nChannel)

    }


}