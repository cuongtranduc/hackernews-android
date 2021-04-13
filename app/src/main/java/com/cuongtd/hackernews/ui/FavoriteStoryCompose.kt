package com.cuongtd.hackernews.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.navigate
import com.cuongtd.hackernews.model.room.Story
import com.cuongtd.hackernews.ui.story.getDomainName
import com.cuongtd.hackernews.ui.theme.LocalPaddings

@Composable
fun FavoriteStoryCompose(story: Story) {
    val navController = AppNavController.current
    Column(modifier = Modifier.clickable {
        navController.navigate("Content?objectID=${story.objectID}/url=${story.url}/title=${story.title}")
    }) {
        Column(
            modifier = Modifier.padding(
                horizontal = LocalPaddings.current.defaultPadding,
                vertical = LocalPaddings.current.smallPadding
            )
        ) {
            Text(
                text = story.title,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface,
            )
            Text(
                text = "Source: ${getDomainName(story.url)}",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.secondary,
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(MaterialTheme.colors.secondary)
        )
    }
}