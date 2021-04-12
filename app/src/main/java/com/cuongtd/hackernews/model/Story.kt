import com.google.gson.annotations.SerializedName

data class Story (
	@SerializedName("created_at") val createdAt : String,
	@SerializedName("title") val title : String,
	@SerializedName("url") val url : String,
	@SerializedName("author") val author : String,
	@SerializedName("points") val points : Int,
	@SerializedName("story_text") val storyText : String,
	@SerializedName("comment_text") val commentText : String,
	@SerializedName("num_comments") val numComments : Int,
	@SerializedName("story_id") val storyId : String,
	@SerializedName("story_title") val storyTitle : String,
	@SerializedName("story_url") val storyUrl : String,
	@SerializedName("parent_id") val parentId : String,
	@SerializedName("created_at_i") val createdAtI : Int,
	@SerializedName("_tags") val tags : List<String>,
	@SerializedName("objectID") val objectID : Int,
	@SerializedName("_highlightResult") val highlightResult : HighlightResult
)