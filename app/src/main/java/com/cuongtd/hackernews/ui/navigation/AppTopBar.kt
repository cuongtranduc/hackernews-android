package com.cuongtd.hackernews.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.navigation.NavHostController
import com.cuongtd.hackernews.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import androidx.compose.runtime.*

@Composable
fun AppTopBar(
    navController: NavHostController,
    isDarkThemeFlow: LiveData<Boolean>,
    changeTheme: suspend () -> Unit
) {
    val currentRoute = currentRoute(navController)
    val isContentCompose = currentRoute?.contains("Content")

    if (isContentCompose == true) {
        ContentTopAppBar(currentRoute = currentRoute, navController)
    } else {
        MainTopAppBar(currentRoute = currentRoute, isDarkThemeFlow, changeTheme)
    }
}

@Composable
fun MainTopAppBar(
    currentRoute: String?,
    isDarkThemeFlow: LiveData<Boolean>,
    changeTheme: suspend () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val isDarkTheme by isDarkThemeFlow.observeAsState()
    val interactionSource = remember { MutableInteractionSource() }

    TopAppBar(
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.primaryVariant,
        title = {
            Text(
                text = currentRoute.toString(),
                style = MaterialTheme.typography.caption,
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        actions = {
            Column(modifier = Modifier.clickable(
                interactionSource = interactionSource,
                indication = null
            ) { coroutineScope.launch { changeTheme() } }) {
                if (isDarkTheme!!) {
                    Icon(
                        painterResource(id = R.drawable.ic_dark_mode),
                        contentDescription = "Localized description",
                        modifier = Modifier
                            .padding(all = 10.dp)
                            .size(24.dp)
                    )
                } else {
                    Icon(
                        painterResource(id = R.drawable.ic_light_mode),
                        contentDescription = "Localized description",
                        modifier = Modifier
                            .padding(all = 10.dp)
                            .size(24.dp),
                        tint = Color.White
                    )
                }
            }
        }
    )
}

@Composable
fun ContentTopAppBar(currentRoute: String?, navController: NavHostController) {
    TopAppBar(
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.primaryVariant,
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = navController.currentBackStackEntry?.arguments?.getString("url")
                        .toString(),
                    style = MaterialTheme.typography.caption,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Left,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        },
        navigationIcon = {
            Column(modifier = Modifier
                .clickable {
                    navController.popBackStack()
                }
                .padding(all = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
//        actions = {
//            Column(modifier = Modifier.width(30.dp)) {}
//        }
    )
}