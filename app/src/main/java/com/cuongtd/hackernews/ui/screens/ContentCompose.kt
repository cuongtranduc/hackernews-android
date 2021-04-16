package com.cuongtd.hackernews.ui.screens

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.cuongtd.hackernews.viewmodel.FavoriteViewModel
import com.cuongtd.hackernews.model.room.Story as StoryEntity

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ContentCompose(
    viewModel: FavoriteViewModel,
    storyId: String,
    urlToRender: String = "https://github.com/",
    storyTitle: String = ""
) {
    val storyInRoom = viewModel.getFavoriteById(storyId)?.observeAsState()
    var progress by remember {
        mutableStateOf((0.1f))
    }

    val isFavorite = storyInRoom?.value != null
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        if (progress != 1f) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp),
                backgroundColor = Color.White,
                progress = progress,
                color = Color(0xFF1da1f2)
            )
        }
        Column(modifier = Modifier.weight(1F)) {
            AndroidView(factory = {
                WebView(it).apply {
                    settings.javaScriptEnabled = true
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webChromeClient = object : WebChromeClient() {
                        override fun onProgressChanged(view: WebView?, newProgress: Int) {
                            progress = (newProgress / 100f).toFloat()
                        }
                    }
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
//                Icon(
//                    imageVector = Icons.Filled.KeyboardArrowLeft,
//                    contentDescription = "Localized description",
//                    modifier = Modifier.size(28.dp)
//                )
//                Icon(
//                    imageVector = Icons.Filled.KeyboardArrowRight,
//                    contentDescription = "Localized description",
//                    modifier = Modifier.size(28.dp)
//                )
            }
            Row(modifier = Modifier.padding(end = 25.dp)) {
                if (isFavorite == true) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        tint = MaterialTheme.colors.primaryVariant,
                        contentDescription = "Localized description",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                viewModel.deleteFavoriteStory(storyInRoom?.value!!)
                            },
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        tint = MaterialTheme.colors.secondary,
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
