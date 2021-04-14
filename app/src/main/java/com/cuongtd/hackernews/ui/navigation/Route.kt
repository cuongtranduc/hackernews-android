package com.cuongtd.hackernews.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.currentBackStackEntryAsState
import com.cuongtd.hackernews.R
import com.cuongtd.hackernews.ui.FavoriteCompose
import com.cuongtd.hackernews.ui.HomeCompose
import com.cuongtd.hackernews.ui.SettingCompose
import com.cuongtd.hackernews.viewmodel.FavoriteViewModel
import com.cuongtd.hackernews.viewmodel.NewStoryViewModel
import com.cuongtd.hackernews.viewmodel.TopStoryViewModel

sealed class Route(
    val route: String,
    val icon: Int,
    val content: @Composable (backStackEntry: NavBackStackEntry, viewModel: ViewModel, navController: NavHostController) -> Unit
) {
    object Home :
        Route(
            "Home",
            R.drawable.ic_home,
            { backStackEntry, viewModel, navController -> HomeCompose(viewModel as NewStoryViewModel) })

    object Top :
        Route(
            "Top",
            R.drawable.ic_new,
            { backStackEntry, viewModel, navController -> TopCompose(viewModel as TopStoryViewModel) })

//    object Best :
//        Route(
//            "Best",
//            R.drawable.ic_star_filled,
//            { backStackEntry, viewModel, navController -> HomeCompose() })

    object Favorite :
        Route(
            "Favorite",
            R.drawable.ic_favorite_filled,
            { backStackEntry, viewModel, navController ->
                FavoriteCompose(
                    viewModel as FavoriteViewModel,
                    navController
                )
            })

    object Setting :
        Route(
            "Setting",
            R.drawable.ic_setting,
            { backStackEntry, viewModel, navController -> SettingCompose() })
}

val Routes = listOf(
    Route.Home,
    Route.Top,
//    Route.Best,
    Route.Favorite,
    Route.Setting,
)

@Composable
public fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.arguments?.getString(KEY_ROUTE) ?: ""
}
