package com.example.flowsyroomfrancisco.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.flowsyroomfrancisco.data.sources.remote.di.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext private val context: Context) {
    companion object{
        private val accessTokenKey = stringPreferencesKey("jwt_token")
        private val refreshTokenKey = stringPreferencesKey("refresh_jwt_token")
    }

    fun getAccessToken(): Flow<String?>{
        return context.dataStore.data.map {
            preferences ->
            preferences[accessTokenKey]
        }
    }


    suspend fun saveAccessToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[accessTokenKey] = token
        }
    }

    suspend fun deleteAccessToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(accessTokenKey)
        }
    }

    fun getRefreshToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[refreshTokenKey]
        }
    }

    suspend fun saveRefreshToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[refreshTokenKey] = token
        }
    }

    suspend fun deleteRefreshToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(refreshTokenKey)
        }
    }
}