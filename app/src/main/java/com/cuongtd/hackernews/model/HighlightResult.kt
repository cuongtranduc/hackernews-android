package com.cuongtd.hackernews.model

import com.google.gson.annotations.SerializedName

data class HighlightResult (
	@SerializedName("title") val title : Title,
	@SerializedName("url") val url : Url,
	@SerializedName("author") val author : Author
)