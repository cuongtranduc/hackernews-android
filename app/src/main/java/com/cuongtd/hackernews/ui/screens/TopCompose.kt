package com.cuongtd.hackernews.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.cuongtd.hackernews.ui.story.StoryListCompose
import com.cuongtd.hackernews.viewmodel.TopStoryViewModel

@Composable
fun TopCompose(viewModel: TopStoryViewModel) {
    val disableLoadingMore: Boolean by viewModel.disableLoadingMore.observeAsState(true)
    val isLoadingMore: Boolean by viewModel.disableLoadingMore.observeAsState(false)
    val isRefreshing: Boolean by viewModel.disableLoadingMore.observeAsState(false)

    StoryListCompose(
        viewModel.stories,
        { viewModel.getStories() },
        disableLoadingMore,
        isLoadingMore,
        isRefreshing,
        { viewModel.refreshStories() }
    )}