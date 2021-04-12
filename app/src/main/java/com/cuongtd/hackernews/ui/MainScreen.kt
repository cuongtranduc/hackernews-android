package com.cuongtd.hackernews.ui

import Story
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.cuongtd.hackernews.ui.navigation.AppBottomNavigator
import com.cuongtd.hackernews.ui.navigation.AppNavigation
import com.cuongtd.hackernews.ui.navigation.AppTopBar
import com.cuongtd.hackernews.ui.navigation.currentRoute
import com.cuongtd.hackernews.ui.theme.LocalPaddings
import com.cuongtd.hackernews.ui.theme.Paddings
import com.cuongtd.hackernews.viewmodel.StoryViewModel

val AppNavController = compositionLocalOf<NavHostController>() { error("NavHostController error") }

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    CompositionLocalProvider(
        AppNavController provides navController
    ) {
        Scaffold(
            topBar = {
                AppTopBar(navController)
            },
            bottomBar = {
                if (currentRoute(navController) != "Content") {
                    AppBottomNavigator(navController)
                }
            },
        ) {
            Column(modifier = Modifier.padding(bottom = 58.dp)) {
                AppNavigation(navController)
            }
        }
    }
}

