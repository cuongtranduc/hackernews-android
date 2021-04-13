package com.cuongtd.hackernews.ui.screens

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModel
import com.cuongtd.hackernews.viewmodel.StoryViewModel
import kotlinx.coroutines.launch
import com.cuongtd.hackernews.model.room.Story as StoryEntity

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ContentCompose(
    viewModel: StoryViewModel,
    storyId: String,
    urlToRender: String = "https://github.com/",
    storyTitle: String = ""
) {
    val storyInRoom = viewModel.getSingle(storyId)?.observeAsState()

    val isFavorite = if (storyInRoom?.value == null) false else true
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.weight(1F)) {
            AndroidView(factory = {
                WebView(it).apply {
                    settings.javaScriptEnabled = true
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient()
                    loadUrl(urlToRender)
                }
            }, update = {
                it.loadUrl(urlToRender)
            })
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row() {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(28.dp)
                )
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(28.dp)
                )
            }
            Row(modifier = Modifier.padding(end = 25.dp)) {
                if (isFavorite == true) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Localized description",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                viewModel.deleteFavoriteStory(storyInRoom?.value!!)
                            },
                        tint = MaterialTheme.colors.primary
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = "Localized description",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                viewModel.addFavoriteStory(
                                    StoryEntity(
                                        uid = 0,
                                        title = storyTitle,
                                        url = urlToRender,
                                        objectID = storyId,
                                    )
                                )
                                Toast
                                    .makeText(
                                        context,
                                        "Added to Favorite",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }
                    )
                }
            }
        }
    }
}
