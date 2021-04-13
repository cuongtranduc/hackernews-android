package com.cuongtd.hackernews.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.currentBackStackEntryAsState
import com.cuongtd.hackernews.R
import com.cuongtd.hackernews.ui.FavoriteCompose
import com.cuongtd.hackernews.ui.HomeCompose
import com.cuongtd.hackernews.ui.SettingCompose
import com.cuongtd.hackernews.viewmodel.StoryViewModel

sealed class Route(
    val route: String,
    val icon: Int,
    val content: @Composable (backStackEntry: NavBackStackEntry, viewModel: StoryViewModel, navController: NavHostController) -> Unit
) {
    object Home :
        Route(
            "Home",
            R.drawable.ic_home,
            { backStackEntry, viewModel, navController -> HomeCompose(viewModel, navController) })

    object New :
        Route(
            "New",
            R.drawable.ic_new,
            { backStackEntry, viewModel, navController -> NewCompose(viewModel, navController) })

    object Best :
        Route(
            "Best",
            R.drawable.ic_star_filled,
            { backStackEntry, viewModel, navController -> HomeCompose(viewModel, navController) })

    object Favorite :
        Route(
            "Favorite",
            R.drawable.ic_favorite_filled,
            { backStackEntry, viewModel, navController -> FavoriteCompose(viewModel, navController) })

    object Setting :
        Route(
            "Setting",
            R.drawable.ic_setting,
            { backStackEntry, viewModel, navController -> SettingCompose() })
}

val Routes = listOf(
    Route.Home,
    Route.New,
//    Route.Best,
    Route.Favorite,
    Route.Setting,
)

@Composable
public fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.arguments?.getString(KEY_ROUTE)
}
