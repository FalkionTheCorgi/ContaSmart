package com.example.accountspayable.GoogleDrive

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.accountspayable.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.FileContent
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.api.services.drive.model.File
import kotlinx.coroutines.*
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class GoogleDriveService(
    ctx : Context
){

    val context = ctx

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)

    private val dataBaseArchives = listOf(
        context.getString(R.string.database_conta_smart),
        context.getString(R.string.database_conta_smart_shm),
        context.getString(R.string.database_conta_smart_wal),
        context.getString(R.string.google_app_measurement_local),
        context.getString(R.string.com_google_android_datatransport_events)
    )

    fun getGoogleSignInClient(): GoogleSignInClient {
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestScopes(Scope(DriveScopes.DRIVE_FILE), Scope(DriveScopes.DRIVE))
            .build()

        return GoogleSignIn.getClient(context, signInOptions)
    }

    private fun returnGoogleDriveAccount(): Drive? {

        GoogleSignIn.getLastSignedInAccount(context)?.let { googleAccount ->

            // get credentials
            val credential = GoogleAccountCredential.usingOAuth2(
                context, listOf(DriveScopes.DRIVE, DriveScopes.DRIVE_FILE)
            )

            // get Drive Instance
            try {
                credential.selectedAccount = googleAccount.account
                return Drive
                    .Builder(
                        AndroidHttp.newCompatibleTransport(),
                        JacksonFactory.getDefaultInstance(),
                        credential
                    )
                    .setApplicationName(context.getString(R.string.app_name))
                    .build()
            }catch (e: IOException){
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }

        }

        return null

    }

    fun createFolderGoogleDrive(){

        coroutineScope.launch {
            // Define a Folder
            val gFolder = File()
            val drive = returnGoogleDriveAccount()
            // Set file name and MIME
            gFolder.name = context.getString(R.string.app_name)
            gFolder.mimeType = context.getString(R.string.mime_type_folder)
            //gFolder.parents = Collections.singletonList("root")

            if (!checkFolderExists(drive, gFolder.name, null)){
                drive?.files()?.create(gFolder)?.setFields("id")?.execute()
            }

        }

    }

    fun uploadFile(
        context: Context
    ){
        dataBaseArchives.forEach { dado ->

            val databasePath =
                context.getDatabasePath(dado).absolutePath// Caminho para o arquivo do banco de dados
            val folderId = getFolderIdByName(returnGoogleDriveAccount(), context.getString(R.string.app_name))
            val fileMetadata = File()
            fileMetadata.name = dado
            fileMetadata.parents = listOf(folderId)
            val idArchive = getFileByName(returnGoogleDriveAccount(), folderId ?: "", dado)
            val file = FileContent("mime/type", java.io.File(databasePath))

            if (idArchive == null) {
                try {
                    returnGoogleDriveAccount()?.files()?.create(fileMetadata, file)
                        ?.setFields("id")
                        ?.execute()
                } catch (e: IOException) {
                    Log.d("ERROR", e.message.toString())
                }
            }else{
                try {
                    returnGoogleDriveAccount()?.files()?.update(idArchive, null, file)
                        ?.execute()
                } catch (e: IOException) {
                    Log.d("ERROR", e.message.toString())
                }
            }
        }

    }

    @Throws(IOException::class)
    private fun  checkFolderExists(
        driveService: Drive?,
        folderName: String,
        parentId: String?
    ): Boolean {
        if (driveService != null) {
            var query =
                "mimeType='application/vnd.google-apps.folder' and name='$folderName'"
            if (parentId != null && parentId.isNotEmpty()) {
                query += " and '$parentId' in parents"
            }
            return try {
                val result = driveService.files().list()
                    .setQ(query)
                    .setSpaces("drive")
                    .execute()
                val files = result.files
                files != null && files.isNotEmpty()
            } catch (e: IOException) {
                false
            }
        } else {
            return false
        }
    }

    @Throws(IOException::class)
    private fun getFolderIdByName(driveService: Drive?, folderName: String): String? {
        return if (driveService != null) {
            val query =
                "mimeType='application/vnd.google-apps.folder' and name='$folderName'"
            val result = driveService.files().list()
                .setQ(query)
                .setSpaces("drive")
                .execute()
            val folders = result.files
            if (folders.isNotEmpty()) {
                folders[0].id
            } else null
        }else{
            null
        }
    }

    @Throws(IOException::class)
    private fun getFileByName(driveService: Drive?, folderId: String, fileName: String): String? {
        if (driveService != null) {
            val query =
                "mimeType!='application/vnd.google-apps.folder' and name='$fileName' and '$folderId' in parents"
            val result = driveService.files().list()
                .setQ(query)
                .setSpaces("drive")
                .setFields("files(id)")
                .execute()
            val files = result.files
            return if (files.isNotEmpty()) {
                files[0].id
            } else null
        }else{
            return null
        }
    }

    @Throws(IOException::class)
    private fun getFilesFromFolder(driveService: Drive?, folderId: String?): List<File?>? {
        return if(driveService == null || folderId == null) {
            null
        }else{
            val query = "'$folderId' in parents"
            val result = driveService.files().list()
                .setQ(query)
                .setSpaces("drive")
                .execute()
            result.files
        }
    }

    fun copyFilesGoogleDriveToRoomFolder(
        wait: () -> Unit,
        start: () -> Unit,
        finished: () -> Unit,
        failure: () -> Unit
    ){
        coroutineScope.launch {
            wait()
            val list = getFilesFromFolder(
                driveService = returnGoogleDriveAccount(),
                folderId =  getFolderIdByName(
                    returnGoogleDriveAccount(),
                    context.getString(R.string.app_name)
                )
            )

            if(list?.isEmpty() == true || list == null){

                failure()

            } else {
                start()
                list.forEach { item ->

                    copyFileToRoom(
                        driveService = returnGoogleDriveAccount(),
                        file = item
                    )

                }
                finished()
            }

        }

    }

    @Throws(IOException::class)
    private fun copyFileToRoom(driveService: Drive?, file: File?) {

        if(driveService != null) {
            val destinationFile = context.getDatabasePath(file?.name).absolutePath
            FileOutputStream(destinationFile).use { outputStream ->
                try {
                    driveService.files().get(file?.id)
                        .executeMediaAndDownloadTo(outputStream)
                } catch (e: IOException) {

                    Log.d("GOOGLE DRIVE ERROR", "arquivo: ${file?.name}\n, error: ${e.message.toString()}")

                }
            }
        }
    }


}