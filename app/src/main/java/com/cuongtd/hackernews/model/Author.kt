import com.google.gson.annotations.SerializedName

data class Author (
	@SerializedName("value") val value : String,
	@SerializedName("matchLevel") val matchLevel : String,
	@SerializedName("matchedWords") val matchedWords : List<String>
)