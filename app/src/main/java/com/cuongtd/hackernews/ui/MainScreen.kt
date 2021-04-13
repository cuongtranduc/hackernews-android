package com.cuongtd.hackernews.ui

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cuongtd.hackernews.ui.navigation.AppBottomNavigator
import com.cuongtd.hackernews.ui.navigation.AppNavigation
import com.cuongtd.hackernews.ui.navigation.AppTopBar
import com.cuongtd.hackernews.ui.navigation.currentRoute

val AppNavController = compositionLocalOf<NavHostController>() { error("NavHostController error") }

@Composable
fun MainScreen(context: Context) {
    val navController = rememberNavController()
    val isContentCompose = currentRoute(navController).toString().contains("Content")

    CompositionLocalProvider(
        AppNavController provides navController
    ) {
        Scaffold(
            topBar = {
                AppTopBar(navController)
            },
            bottomBar = {
                if (isContentCompose == false) {
                    AppBottomNavigator(navController)
                }
            },
        ) {
            Column(modifier = Modifier.padding(bottom = if (isContentCompose == false) 58.dp else 0.dp)) {
                AppNavigation(context, navController)
            }
        }
    }
}

