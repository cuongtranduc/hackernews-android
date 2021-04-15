package com.cuongtd.hackernews

import android.content.Context
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import com.cuongtd.hackernews.ui.MainScreen
import com.cuongtd.hackernews.ui.theme.HackerNewsTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.prefs.Preferences
import androidx.compose.runtime.*
import androidx.datastore.migrations.SharedPreferencesMigration
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

val Context.dataStore by preferencesDataStore("user_preferences")

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this.applicationContext as Context

        val isDarkThemeKey = booleanPreferencesKey("is_dark_theme")
        val isDarkThemeFlow = runBlocking {
            context.dataStore.data
                .map { preferences ->
                    // No type safety.
                    preferences[isDarkThemeKey] ?: isDarkThemeOn()
                }.first()
        }
        var isDarkTheme = MutableLiveData(isDarkThemeFlow)

        suspend fun changeTheme() {
            context.dataStore.edit { preferences ->
                val currentThemeValue = preferences[isDarkThemeKey] ?: false
                preferences[isDarkThemeKey] = !currentThemeValue
                isDarkTheme.value = !currentThemeValue
            }
        }

        setContent {
            HackerNewsTheme(isDarkTheme) {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen(context, isDarkTheme, ::changeTheme)
                }
            }
        }
    }

    fun Context.isDarkThemeOn(): Boolean {
        return resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES
    }
}
