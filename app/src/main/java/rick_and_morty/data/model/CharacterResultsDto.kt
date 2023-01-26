package rick_and_morty.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class CharacterResultsDto(
    @Json(name = "created")
    val created: String = "",
    @Json(name = "episode")
    val episode: List<String> = emptyList(),
    @Json(name = "gender")
    val gender: String = "",
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "image")
    val image: String = "",
    @Json(name = "location")
    val locationDto: LocationDto? = null,
    @Json(name = "name")
    val name: String = "",
    @Json(name = "origin")
    val originDto: OriginDto? = null,
    @Json(name = "species")
    val species: String = "",
    @Json(name = "status")
    val status: String = "",
    @Json(name = "type")
    val type: String = "",
    @Json(name = "url")
    val url: String = ""
    )
