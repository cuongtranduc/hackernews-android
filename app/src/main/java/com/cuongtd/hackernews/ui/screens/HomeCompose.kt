package com.cuongtd.hackernews.ui

import Story
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.cuongtd.hackernews.viewmodel.StoryViewModel

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

@Composable
fun HomeCompose(viewModel: StoryViewModel = StoryViewModel(), navController: NavController) {
    val stories: List<Story> by viewModel.topStories.observeAsState(listOf())
    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
        items(items = stories) { story ->
            StoryCompose(story, navController)
        }
    }
}