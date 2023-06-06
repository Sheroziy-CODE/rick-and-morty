package rick_and_morty.data.model.episodes

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import rick_and_morty.data.model.CharacterResultsDto
import rick_and_morty.data.model.InfoDto

@JsonClass(generateAdapter = true)
data class EpisodeResponseDto(
    @Json(name = "info")
    val infoDto: InfoDto,
    @Json(name = "results")
    val results: List<EpisodesResultDto>
)