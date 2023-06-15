package com.example.accountspayable.GoogleDrive

import android.content.Context
import android.util.Log
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


class GoogleDriveService(
    ctx : Context
){

    val context = ctx

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)

    private val dataBaseArchives = listOf(
        "databaseContaSmart",
        "databaseContaSmart-shm",
        "databaseContaSmart-wal",
        "google_app_measurement_local.db",
        "google_app_measurement_local.db-journal",
        "com.google.android.datatransport.events",
        "com.google.android.datatransport.events-journal"

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
            credential.selectedAccount = googleAccount.account!!

            // get Drive Instance
            return Drive
                .Builder(
                    AndroidHttp.newCompatibleTransport(),
                    JacksonFactory.getDefaultInstance(),
                    credential
                )
                .setApplicationName(context.getString(R.string.app_name))
                .build()

        }

        return null

    }

    fun createFolderGoogleDrive(){

        coroutineScope.launch {
            // Define a Folder
            val gFolder = File()
            val drive = returnGoogleDriveAccount()
            // Set file name and MIME
            gFolder.name = "ContaSmart"
            gFolder.mimeType = "application/vnd.google-apps.folder"

            if (!checkFolderExists(drive, gFolder.name, null)){
                drive?.Files()?.create(gFolder)?.setFields("id")?.execute()
            }

            // You can also specify where to create the new Google folder
            // passing a parent Folder Id
            /*val parents: MutableList<String> = ArrayList(1)
            parents.add("your_parent_folder_id_here")
            gFolder.parents = parents*/
        }

    }

    fun uploadFile(
        context: Context
    ){
        dataBaseArchives.forEach { dado ->

            val databasePath =
                context.getDatabasePath(dado).absolutePath// Caminho para o arquivo do banco de dados
            val folderId = getFolderIdByName(returnGoogleDriveAccount(), "ContaSmart")
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
    private fun checkFolderExists(
        driveService: Drive?,
        folderName: String,
        parentId: String?
    ): Boolean {
        if (driveService != null) {
            var query =
                "mimeType='application/vnd.google-apps.folder' and name='$folderName'"
            if (parentId != null && !parentId.isEmpty()) {
                query += " and '$parentId' in parents"
            }
            val result = driveService.files().list()
                .setQ(query)
                .setSpaces("drive")
                .execute()
            val files = result.files
            return files.isNotEmpty()
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
        start: () -> Unit,
        finished: () -> Unit
    ){
        coroutineScope.launch {
            start()
            val list = getFilesFromFolder(
                driveService = returnGoogleDriveAccount(),
                folderId =  getFolderIdByName(
                    returnGoogleDriveAccount(),
                    "ContaSmart"
                )
            )

            list?.forEach { item ->

                copyFileToRoom(
                    driveService = returnGoogleDriveAccount(),
                    file = item
                )

            }

            finished()

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