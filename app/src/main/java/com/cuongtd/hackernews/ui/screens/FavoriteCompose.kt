package com.cuongtd.hackernews.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cuongtd.hackernews.model.room.Story
import com.cuongtd.hackernews.viewmodel.FavoriteViewModel
import androidx.compose.runtime.*

@Composable
fun FavoriteCompose(viewModel: FavoriteViewModel) {
    val stories: List<Story>? by viewModel.favoriteStories.observeAsState()
    val listState = rememberLazyListState()

    if (stories!!.size <= 0) {
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Nothing to show",
                style = MaterialTheme.typography.body1,
                fontSize = 18.sp
            )
        }
    } else {
        LazyColumn(state = listState) {
            items(items = stories!!) { story ->
                FavoriteStoryCompose(story)
            }
        }
    }
}