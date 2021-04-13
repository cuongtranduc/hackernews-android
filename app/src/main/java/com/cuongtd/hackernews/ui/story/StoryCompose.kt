package com.cuongtd.hackernews.ui.story

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.navigate
import com.cuongtd.hackernews.Utils
import com.cuongtd.hackernews.model.Story
import com.cuongtd.hackernews.ui.AppNavController
import com.cuongtd.hackernews.ui.theme.LocalPaddings
import java.net.URI

fun getDomainName(url: String?): String {
    if (url == null) return ""
    val uri = URI(url)
    val domain = uri.host
    return if (domain.startsWith("www.")) domain.substring(4) else domain
}

@Composable
fun StoryCompose(story: Story) {
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
            Row(modifier = Modifier.padding(top = 2.dp)) {
                Text(
                    text = story.points.toString() + " points",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier.padding(end = LocalPaddings.current.smallPadding)
                )
                Text(
                    text = story.numComments.toString() + " comments",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier.padding(end = 5.dp)
                )
                Text(
                    text = Utils.formatTimeAgo(story.createdAt),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.secondary
                )
            }
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