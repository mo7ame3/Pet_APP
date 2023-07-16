package com.example.petapp.sharedpreference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SharedPreference(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")
        val TOKEN = stringPreferencesKey("token")
        val ROLE = stringPreferencesKey("role")
    }

    val getToken: Flow<String?> = context.dataStore.data
        .map { Preferences ->
            Preferences[TOKEN] ?: ""
        }
    val getRole: Flow<String?> = context.dataStore.data
        .map { Preferences ->
            Preferences[ROLE] ?: ""
        }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { Preferences ->
            Preferences[TOKEN] = token
        }
    }

    suspend fun saveRole(role: String) {
        context.dataStore.edit { Preferences ->
            Preferences[ROLE] = role
        }
    }

    suspend fun removeUser() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }


}