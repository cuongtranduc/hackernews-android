package com.cuongtd.hackernews.ui.screens.webview

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.cuongtd.hackernews.model.room.Story
import com.cuongtd.hackernews.viewmodel.FavoriteViewModel

@Composable
fun FavoriteIconCompose(
    viewModel: FavoriteViewModel,
    storyId: String,
    urlToRender: String = "https://github.com/",
    storyTitle: String = ""
) {
    val context = LocalContext.current

    val storyInRoom = viewModel.getFavoriteById(storyId)?.observeAsState()
    val isFavorite = storyInRoom?.value != null

    if (isFavorite) {
        Icon(
            imageVector = Icons.Filled.Star,
            tint = MaterialTheme.colors.primary,
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
                        Story(
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