package com.cuongtd.hackernews.ui

import Story
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.cuongtd.hackernews.Utils
import com.cuongtd.hackernews.ui.theme.HackerNewsTheme
import com.cuongtd.hackernews.ui.theme.LocalPaddings

@Composable
fun StoryCompose(story: Story, navController: NavController) {

    Column(modifier = Modifier.clickable {
        navController.navigate("Content?url=${story.url}")
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
//        Text(text = story.url)
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(MaterialTheme.colors.secondary)
        )
    }
}