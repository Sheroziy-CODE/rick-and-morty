package rick_and_morty.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RickAndMortyResponseDto(
    @Json(name = "info")
    val infoDto: InfoDto,
    @Json(name = "results")
    var results: List<CharacterResultsDto>
)