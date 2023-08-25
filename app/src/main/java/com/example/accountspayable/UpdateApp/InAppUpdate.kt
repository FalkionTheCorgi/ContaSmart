package com.example.accountspayable.UpdateApp

import android.app.Activity
import android.content.Context
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability

class InAppUpdate(
    activity: Activity,
    context: Context
) {

    val appUpdateManager = AppUpdateManagerFactory.create(context)
    val activityApp = activity

    val MY_REQUEST_CODE = 999

    // Returns an intent object that you use to check for an update.
    val appUpdateInfoTask = appUpdateManager.appUpdateInfo

    fun verify(){

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                // This example applies an immediate update. To apply a flexible update
                // instead, pass in AppUpdateType.FLEXIBLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {

                startUpdate(
                    appUpdateInfo, AppUpdateType.IMMEDIATE, activityApp
                )

            }
        }

    }

    fun startUpdate(
        appUpdateInfo: AppUpdateInfo,
        appUpdateType: Int,
        activity: Activity
    ){

        appUpdateManager.startUpdateFlowForResult(
            appUpdateInfo,
            appUpdateType,
            activity,
            MY_REQUEST_CODE
        )
    }

    fun inProgressUpdate(){

        appUpdateManager.appUpdateInfo.addOnSuccessListener { updateInfo ->

            if (updateInfo.updateAvailability()== UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                && updateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)){
                appUpdateManager.startUpdateFlowForResult(
                    updateInfo, AppUpdateType.IMMEDIATE, activityApp, MY_REQUEST_CODE
                )
            }

        }

    }

}