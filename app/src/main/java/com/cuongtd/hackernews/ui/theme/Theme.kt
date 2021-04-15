package com.cuongtd.hackernews.ui.theme

import android.view.Window
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData

private val DarkColorPalette = darkColors(
    primary = Color(0xFFD8661B),
    primaryVariant = Color(0xFF121212), // 25292d
    secondary = Color(0xFF68676c),
    background = Color.Black,
    surface = Color.Black,
    onPrimary = Color(0xFFEBEBEB),
    onSecondary = Color(0xFFEBEBEB),
    onBackground = Color(0xFFEBEBEB),
    onSurface = Color(0xFFEBEBEB),
)

private val LightColorPalette = lightColors(
    primary = Color(0xFFD8661B),
    primaryVariant = Color(0xFFD57333),
    secondary = Color(0xFF828282),
    background = Color(0xFFF6F6EF),
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color(0xFF121212),
    onBackground = Color(0xFF121212),
    onSurface = Color.Black,
)

@Immutable
data class Paddings(
    val defaultPadding: Dp = 16.dp,
    val tinyPadding: Dp = 4.dp,
    val smallPadding: Dp = 8.dp,
    val mediumPadding: Dp = 20.dp,
    val largePadding: Dp = 24.dp
)

internal val LocalPaddings = staticCompositionLocalOf { Paddings() }

@Composable
fun HackerNewsTheme(isDarkThemeFlow: LiveData<Boolean>, window: Window, content: @Composable() () -> Unit) {
    val isDarkTheme by isDarkThemeFlow.observeAsState()

    val colors = if (isDarkTheme!!) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    // change statusbar background color
    window.statusBarColor = colors.primaryVariant.toArgb()

    CompositionLocalProvider(
        LocalPaddings provides Paddings()
    ) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}