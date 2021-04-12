package com.cuongtd.hackernews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.cuongtd.hackernews.ui.HomeCompose
import com.cuongtd.hackernews.ui.theme.HackerNewsTheme
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import com.cuongtd.hackernews.ui.MainScreen
import com.cuongtd.hackernews.ui.screens.ContentCompose
import com.cuongtd.hackernews.ui.theme.Paddings

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HackerNewsTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }
}
