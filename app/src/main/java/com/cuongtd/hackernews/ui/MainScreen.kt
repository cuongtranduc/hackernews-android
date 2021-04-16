package com.cuongtd.hackernews.ui

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cuongtd.hackernews.ui.navigation.AppBottomNavigator
import com.cuongtd.hackernews.ui.navigation.AppNavigation
import com.cuongtd.hackernews.ui.navigation.AppTopBar
import com.cuongtd.hackernews.ui.navigation.currentRoute
import com.cuongtd.hackernews.viewmodel.FavoriteViewModel
import com.cuongtd.hackernews.viewmodel.NewStoryViewModel
import com.cuongtd.hackernews.viewmodel.TopStoryViewModel

val AppNavController = compositionLocalOf<NavHostController>() { error("NavHostController error") }

@Composable
fun MainScreen(
    context: Context,
    isDarkThemeFlow: LiveData<Boolean>,
    changeTheme: suspend () -> Unit
) {
    val navController = rememberNavController()
    val isContentCompose = currentRoute(navController).toString().contains("Content")

    val favoriteViewModel = remember { FavoriteViewModel(context) }
    val topStoryViewModel = remember { TopStoryViewModel(context) }
    val newStoryViewModel = remember { NewStoryViewModel(context) }

    CompositionLocalProvider(
        AppNavController provides navController
    ) {
        Scaffold(
            topBar = {
                AppTopBar(
                    navController,
                    isDarkThemeFlow,
                    changeTheme,
                    newStoryViewModel,
                    topStoryViewModel,
                )
            },
            bottomBar = {
                if (!isContentCompose) {
                    AppBottomNavigator(navController)
                }
            },
        ) {
            Column(modifier = Modifier.padding(bottom = if (!isContentCompose) 58.dp else 0.dp)) {
                AppNavigation(
                    navController,
                    newStoryViewModel,
                    topStoryViewModel,
                    favoriteViewModel
                )
            }
        }
    }
}

