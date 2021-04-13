package com.cuongtd.hackernews.model

import com.google.gson.annotations.SerializedName

data class Result (
	@SerializedName("hits") val hits : List<Story>,
	@SerializedName("nbHits") val nbHits : Int,
	@SerializedName("page") val page : Int,
	@SerializedName("nbPages") val nbPages : Int,
	@SerializedName("hitsPerPage") val hitsPerPage : Int,
	@SerializedName("exhaustiveNbHits") val exhaustiveNbHits : Boolean,
	@SerializedName("query") val query : String,
	@SerializedName("params") val params : String,
	@SerializedName("processingTimeMS") val processingTimeMS : Int
)