package com.cuongtd.hackernews.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.cuongtd.hackernews.model.Story
import com.cuongtd.hackernews.viewmodel.StoryViewModel

@Composable
fun HomeCompose(viewModel: StoryViewModel, navController: NavController) {
    val stories: List<Story> by viewModel.topStories.observeAsState(listOf())
    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
        items(items = stories) { story ->
            StoryCompose(story)
        }
    }
}