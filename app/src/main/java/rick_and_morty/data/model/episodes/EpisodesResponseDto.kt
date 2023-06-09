package rick_and_morty.data.model.episodes

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EpisodesResponseDto(
    @Json(name = "info")
    val infoDto: EpisodesInfoDto,
    @Json(name = "results")
    val results: List<EpisodeResultDto>
)