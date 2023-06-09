package rick_and_morty.data.model.episodes

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EpisodesInfoDto(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)