package com.cuongtd.hackernews

import com.github.marlonlom.utilities.timeago.TimeAgo
import java.text.SimpleDateFormat


class Utils {
    companion object {
        fun formatTimeAgo(dateString: String): String {
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(dateString)
            return TimeAgo.using(date.time)
        }
    }
}