package com.cuongtd.hackernews.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.cuongtd.hackernews.model.Story
import com.cuongtd.hackernews.ui.story.StoryListCompose
import com.cuongtd.hackernews.viewmodel.StoryViewModel

@Composable
fun NewCompose(viewModel: StoryViewModel) {
    StoryListCompose(viewModel.stories)
}