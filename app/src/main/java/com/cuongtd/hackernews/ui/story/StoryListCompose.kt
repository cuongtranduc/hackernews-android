package com.cuongtd.hackernews.ui.story

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.cuongtd.hackernews.model.Story

var isFirstLoad = true

@Composable
fun StoryListCompose(stories: LiveData<List<Story>>) {
    val stories: List<Story> by stories.observeAsState(listOf())
    val listState = rememberLazyListState()
//    var isFirstLoad by remember { mutableStateOf(true) }


    if (stories.isEmpty() && isFirstLoad) {
        isFirstLoad = false
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colors.onBackground,
                strokeWidth = 2.dp
            )
        }
    } else {
        LazyColumn(state = listState) {
            items(items = stories) { story ->
                StoryCompose(story)
            }
        }
    }
}