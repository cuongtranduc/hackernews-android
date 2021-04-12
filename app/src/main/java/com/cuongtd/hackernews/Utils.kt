package com.cuongtd.hackernews

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import com.github.marlonlom.utilities.timeago.TimeAgo;
import java.time.ZoneOffset

import java.time.LocalDateTime

import java.time.format.DateTimeFormatter


class Utils {
    companion object {
        fun formatTimeAgo(dateString: String): String {
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(dateString)
            return TimeAgo.using(date.time)
        }
    }
}