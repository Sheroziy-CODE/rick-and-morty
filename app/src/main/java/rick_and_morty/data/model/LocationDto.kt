package rick_and_morty.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LocationDto(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
)