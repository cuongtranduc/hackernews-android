package com.cuongtd.hackernews

import android.content.Context
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.MutableLiveData
import com.cuongtd.hackernews.ui.MainScreen
import com.cuongtd.hackernews.ui.theme.HackerNewsTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

val Context.dataStore by preferencesDataStore("user_preferences")

class MainActivity : ComponentActivity() {
    var doubleBackToExitPressedOnce = false

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
            HackerNewsTheme(isDarkTheme, window = window) {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen(context, isDarkTheme, ::changeTheme)
                }
            }
        }
    }

    private fun Context.isDarkThemeOn(): Boolean {
        return resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES
    }

    // handle press twice to exit
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            moveTaskToBack(true)
            return
        } else {
            super.onBackPressed()
        }

        doubleBackToExitPressedOnce = true
        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
}
