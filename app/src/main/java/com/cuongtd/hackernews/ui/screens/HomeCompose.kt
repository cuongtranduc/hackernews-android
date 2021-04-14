package com.cuongtd.hackernews.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.cuongtd.hackernews.ui.story.StoryListCompose
import com.cuongtd.hackernews.viewmodel.NewStoryViewModel

@Composable
fun HomeCompose(viewModel: NewStoryViewModel) {
    val disableLoadingMore: Boolean by viewModel.disableLoadingMore.observeAsState(true)
    val isLoadingMore: Boolean by viewModel.disableLoadingMore.observeAsState(false)

    fun getTopStories() {
        viewModel.getStories()
    }

    StoryListCompose(viewModel.stories, ::getTopStories, disableLoadingMore, isLoadingMore)
}