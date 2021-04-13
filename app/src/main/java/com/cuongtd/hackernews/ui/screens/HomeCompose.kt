package com.cuongtd.hackernews.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.cuongtd.hackernews.model.Story
import com.cuongtd.hackernews.ui.story.StoryListCompose
import com.cuongtd.hackernews.viewmodel.StoryViewModel

@Composable
fun HomeCompose(viewModel: StoryViewModel) {
    StoryListCompose(viewModel.topStories)
}