package com.cuongtd.hackernews.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.W200
import androidx.compose.ui.text.font.FontWeight.Companion.W300
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.cuongtd.hackernews.ui.theme.LocalPaddings

@Composable
fun AppBottomNavigator(navController: NavHostController) {
    val currentRoute = currentRoute(navController)

    BottomNavigation(
        modifier = Modifier,
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.background
    ) {
        Routes.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        null,
                        modifier = Modifier.size(
                            if (currentRoute == item.route)
                                LocalPaddings.current.largePadding
                            else LocalPaddings.current.mediumPadding
                        ),
                        tint = if (currentRoute == item.route)
                            MaterialTheme.colors.primary
                        else MaterialTheme.colors.secondary
                    )
                },
                label = {
                    Text(
                        text = item.route,
                        fontSize = 12.sp,
                        fontWeight = W400,
                        color = if (currentRoute == item.route)
                            MaterialTheme.colors.primary
                        else MaterialTheme.colors.secondary,
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route)
                    }
                },
            )
        }
    }
}