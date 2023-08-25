package com.example.accountspayable.Room

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class BackupDataBase {

    val dataBaseArchives = listOf(
        "databaseContaSmart",
        "databaseContaSmart-shm",
        "databaseContaSmart-wal",
        "google_app_measurement_local.db",
        "google_app_measurement_local.db-journal",
    )

    fun exportDatabase(context: Context) {

        dataBaseArchives.forEach {dado ->
            val databasePath = context.getDatabasePath(dado).absolutePath// Caminho para o arquivo do banco de dados
            val externalStorageDir = Environment.getExternalStorageDirectory() // Diretório raiz do armazenamento externo

            val backupDir = File(externalStorageDir, "conta_smart") // Diretório de backup
            backupDir.mkdirs() // Cria o diretório se não existir

            val backupFile = File(backupDir, dado) // Arquivo de backup

            try {
                FileInputStream(databasePath).use { inputStream ->
                    FileOutputStream(backupFile).use { outputStream ->
                        inputStream.copyTo(outputStream)
                        //Toast.makeText(context, "Banco de dados $dado exportado com sucesso!", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: IOException) {
                Log.d("ERROR", e.message.toString())
            }
        }


    }

    fun importarDataBase(context: Context) {
        dataBaseArchives.forEach {dado ->
            val externalStorageDir = Environment.getExternalStorageDirectory()
            val backupDir = File(externalStorageDir, "conta_smart")
            val backupFile = File(backupDir, dado)

            val databasePath = context.getDatabasePath(dado).absolutePath
            val databaseFile = File(databasePath)

            if (backupFile.exists()) {
                try {
                    FileInputStream(backupFile).use { inputStream ->
                        FileOutputStream(databaseFile).use { outputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                    //Toast.makeText(context, "Banco de dados $dado importado com sucesso!", Toast.LENGTH_SHORT).show()
                } catch (e: IOException) {
                    //Toast.makeText(context, "Falha ao importar $dado o banco de dados!", Toast.LENGTH_SHORT).show()
                }
            } else {
                //Toast.makeText(context, "Arquivo de backup $dado não encontrado!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}