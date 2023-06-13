package rick_and_morty.data.model.episodes

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EpisodeResultDto(
    val id: Int,
    val name: String,
    @Json(name = "air_date")
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)