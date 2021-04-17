package com.cuongtd.hackernews.ui.screens.webview

import android.webkit.WebView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WebViewNavigatorIcons(webView: WebView?, canGoBack: Boolean, canGoForward: Boolean) {
    Icon(
        modifier = Modifier
            .size(if (canGoBack) 45.dp else 40.dp)
            .clickable {
                if (canGoBack) webView!!.goBack()
            },
        imageVector = Icons.Filled.ArrowLeft,
        contentDescription = "Localized description",
        tint = if (canGoBack) MaterialTheme.colors.onBackground else MaterialTheme.colors.secondary
    )
    Icon(
        modifier = Modifier
            .size(if (canGoForward) 45.dp else 40.dp)
            .clickable {
                if (canGoForward) webView!!.goBackOrForward(1)
            },
        imageVector = Icons.Filled.ArrowRight,
        contentDescription = "Localized description",
        tint = if (canGoForward) MaterialTheme.colors.onBackground else MaterialTheme.colors.secondary

    )
}