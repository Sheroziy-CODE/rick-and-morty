package rick_and_morty.data.model.episodes

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EpisodeResultDto(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)