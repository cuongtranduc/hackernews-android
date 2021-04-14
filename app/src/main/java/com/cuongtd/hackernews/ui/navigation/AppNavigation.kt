package com.cuongtd.hackernews.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.cuongtd.hackernews.ui.screens.ContentCompose
import com.cuongtd.hackernews.viewmodel.FavoriteViewModel
import com.cuongtd.hackernews.viewmodel.NewStoryViewModel
import com.cuongtd.hackernews.viewmodel.TopStoryViewModel

@Composable
fun AppNavigation(
    context: Context,
    navController: NavHostController,
) {
    val favoriteViewModel = FavoriteViewModel(context)
    val topStoryViewModel = TopStoryViewModel(context)
    val newStoryViewModel = NewStoryViewModel(context)
    NavHost(navController, startDestination = Route.Home.route) {
        composable(Route.Home.route) { backStackEntry ->
            Route.Home.content(backStackEntry, newStoryViewModel, navController)
        }
        composable(Route.Top.route) { backStackEntry ->
            Route.Top.content(backStackEntry, topStoryViewModel, navController)
        }
        composable(Route.Favorite.route) { backStackEntry ->
            Route.Favorite.content(backStackEntry, favoriteViewModel, navController)
        }
        composable(Route.Setting.route) { backStackEntry ->
            Route.Setting.content(backStackEntry, favoriteViewModel, navController)
        }
        composable(
            "Content?objectID={objectID}/url={url}/title={title}",
            arguments = listOf(
                navArgument("objectID") { defaultValue = "" },
                navArgument("url") { defaultValue = "" },
                navArgument("title") { defaultValue = "" })
        ) {
            ContentCompose(
                favoriteViewModel,
                storyId = it.arguments?.getString("objectID")!!,
                urlToRender = it.arguments?.getString("url")!!,
                storyTitle = it.arguments?.getString("title")!!
            )
        }
    }
}
