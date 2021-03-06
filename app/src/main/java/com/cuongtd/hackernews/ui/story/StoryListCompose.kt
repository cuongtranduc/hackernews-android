package com.cuongtd.hackernews.ui.story

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.cuongtd.hackernews.model.Story
import com.puculek.pulltorefresh.PullToRefresh


@Composable
fun StoryListCompose(
    stories: LiveData<List<Story>>,
    getStories: () -> Unit,
    disableLoadingMore: Boolean,
    isLoadingMore: Boolean,
    isRefreshing: Boolean,
    refreshStories: () -> Unit,
) {
    val stories: List<Story>? by stories.observeAsState()
    val listState = rememberLazyListState()

    if (stories!!.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colors.onSecondary,
                strokeWidth = 2.dp
            )
        }
    } else {
        PullToRefresh(
            isRefreshing = isRefreshing,
            progressColor = MaterialTheme.colors.onSecondary,
            onRefresh = {
                refreshStories()
            }
        ) {
            LazyColumn(state = listState, horizontalAlignment = Alignment.CenterHorizontally) {
                itemsIndexed(items = stories!!) { index, story ->
                    StoryCompose(story)
                    if (index == stories!!.lastIndex && !disableLoadingMore && !isLoadingMore) {
                        getStories()
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                                .size(24.dp),
                            color = MaterialTheme.colors.onBackground,
                            strokeWidth = 2.dp
                        )
                    }
                }
            }
        }
    }
}