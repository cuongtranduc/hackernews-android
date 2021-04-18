package com.cuongtd.hackernews.utilities

import android.annotation.SuppressLint
import android.util.Log
import com.github.marlonlom.utilities.timeago.TimeAgo
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

class Helper {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun formatTimeAgo(dateString: String): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"))
            var  utcDate= sdf.parse(dateString)
            return TimeAgo.using(utcDate.time)
        }


        fun getDomainName(url: String?): String {
            if (url == null) return ""
            val uri = URI(url)
            val domain = uri.host
            return if (domain.startsWith("www.")) domain.substring(4) else domain
        }
    }
}