package com.cuongtd.hackernews.utilities

import android.annotation.SuppressLint
import com.github.marlonlom.utilities.timeago.TimeAgo
import java.net.URI
import java.text.SimpleDateFormat

class Helper {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun formatTimeAgo(dateString: String): String {
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(dateString)
            return TimeAgo.using(date.time)
        }


        fun getDomainName(url: String?): String {
            if (url == null) return ""
            val uri = URI(url)
            val domain = uri.host
            return if (domain.startsWith("www.")) domain.substring(4) else domain
        }
    }
}