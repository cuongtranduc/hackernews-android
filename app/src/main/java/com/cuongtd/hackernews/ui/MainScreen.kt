package com.cuongtd.hackernews.ui

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cuongtd.hackernews.dataStore
import com.cuongtd.hackernews.ui.navigation.AppBottomNavigator
import com.cuongtd.hackernews.ui.navigation.AppNavigation
import com.cuongtd.hackernews.ui.navigation.AppTopBar
import com.cuongtd.hackernews.ui.navigation.currentRoute
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val AppNavController = compositionLocalOf<NavHostController>() { error("NavHostController error") }

@Composable
fun MainScreen(
    context: Context,
    isDarkThemeFlow: LiveData<Boolean>,
    changeTheme: suspend () -> Unit
) {
    val navController = rememberNavController()
    val isContentCompose = currentRoute(navController).toString().contains("Content")

    CompositionLocalProvider(
        AppNavController provides navController
    ) {
        Scaffold(
            topBar = {
                AppTopBar(navController, isDarkThemeFlow, changeTheme)
            },
            bottomBar = {
                if (!isContentCompose) {
                    AppBottomNavigator(navController)
                }
            },
        ) {
            Column(modifier = Modifier.padding(bottom = if (!isContentCompose) 58.dp else 0.dp)) {
                AppNavigation(context, navController)
            }
        }
    }
}

