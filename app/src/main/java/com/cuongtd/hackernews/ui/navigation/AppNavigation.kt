package com.cuongtd.hackernews.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.cuongtd.hackernews.ui.HomeCompose
import com.cuongtd.hackernews.ui.screens.ContentCompose
import com.cuongtd.hackernews.viewmodel.StoryViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
) {
    val viewModel = StoryViewModel()
    NavHost(navController, startDestination = Route.Home.route) {
        Routes.forEach {
            composable(it.route) { backStackEntry ->
                it.content(backStackEntry, viewModel, navController)
            }
            composable(
                "Content?url={url}",
                arguments = listOf(navArgument("url") { defaultValue = "" })
            ) {
                ContentCompose(urlToRender = it.arguments?.getString("url")!!)
            }
        }
    }
}
