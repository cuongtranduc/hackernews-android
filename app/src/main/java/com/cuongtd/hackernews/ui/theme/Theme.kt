package com.cuongtd.hackernews.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val DarkColorPalette = darkColors(
    primary = Color(0xFF1da1f2),
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
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
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
fun HackerNewsTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (true) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

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