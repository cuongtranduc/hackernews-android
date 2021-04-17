package com.cuongtd.hackernews.ui.screens.webview

import android.content.ClipData
import android.content.ClipboardManager
import android.webkit.WebView
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

@Composable
fun CopyCompose(webView: WebView?) {
    val context = LocalContext.current

    Icon(
        imageVector = Icons.Filled.ContentCopy,
        tint = MaterialTheme.colors.onBackground,
        contentDescription = "Localized description",
        modifier = Modifier
            .padding(5.dp)
            .size(20.dp)
            .clickable {
                var myClipboard = ContextCompat.getSystemService(
                    context!!,
                    ClipboardManager::class.java
                ) as ClipboardManager
                val clip: ClipData = ClipData.newPlainText("url link", webView!!.url)
                myClipboard.setPrimaryClip(clip)

                Toast
                    .makeText(
                        context,
                        "Copied link to clipboard",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            },
    )
}