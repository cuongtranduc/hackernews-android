package com.cuongtd.hackernews.ui.screens.webview

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

@Composable
fun ShareCompose(urlToRender: String, storyTitle: String) {
    val context = LocalContext.current

    Icon(
        imageVector = Icons.Filled.Share,
        tint = MaterialTheme.colors.onBackground,
        contentDescription = "Localized description",
        modifier = Modifier
            .padding(5.dp)
            .size(20.dp)
            .clickable {
                val i = Intent(Intent.ACTION_SEND)
                i.type = "text/plain"
                i.putExtra(Intent.EXTRA_TEXT, urlToRender)
                ContextCompat.startActivity(context, Intent.createChooser(i, storyTitle), null)
            },
    )
}