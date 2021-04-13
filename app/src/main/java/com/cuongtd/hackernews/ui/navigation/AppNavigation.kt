package com.cuongtd.hackernews.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.cuongtd.hackernews.ui.screens.ContentCompose
import com.cuongtd.hackernews.viewmodel.StoryViewModel

@Composable
fun AppNavigation(
    context: Context,
    navController: NavHostController,
) {
    val viewModel = StoryViewModel(context)
    NavHost(navController, startDestination = Route.Home.route) {
        Routes.forEach {
            composable(it.route) { backStackEntry ->
                it.content(backStackEntry, viewModel, navController)
            }
        }
        composable(
            "Content?objectID={objectID}/url={url}/title={title}",
            arguments = listOf(
                navArgument("objectID") { defaultValue = "" },
                navArgument("url") { defaultValue = "" },
                navArgument("title") { defaultValue = "" })
        ) {
            ContentCompose(
                viewModel,
                storyId = it.arguments?.getString("objectID")!!,
                urlToRender = it.arguments?.getString("url")!!,
                storyTitle = it.arguments?.getString("title")!!
            )
        }
    }
}
