package com.example.accountspayable.DataStore


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStore(private val context: Context){

    companion object{

        private val Context.dataStore : DataStore<Preferences> by preferencesDataStore("dataStore")
        private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        private val BACKUP_MODE_KEY = booleanPreferencesKey("backup_mode")
        private val OPEN_FIRST_TIME = booleanPreferencesKey("open_first_time")
    }

    val getDarkMode: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[DARK_MODE_KEY] ?: false
    }

    val getBackupMode: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[BACKUP_MODE_KEY] ?: false
    }

    val getOpenFirstTime: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[OPEN_FIRST_TIME] ?: false
    }

    suspend fun saveDarkMode(isDarkMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = isDarkMode
        }
    }

    suspend fun saveBackupMode(isBackupMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[BACKUP_MODE_KEY] = isBackupMode
        }
    }

    suspend fun saveOpenFirstTime(isOpenFirstTime: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[OPEN_FIRST_TIME] = isOpenFirstTime
        }
    }

}