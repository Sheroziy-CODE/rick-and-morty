package rick_and_morty.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InfoDto(
    @Json(name = "count")
    val count: Int,
    @Json(name = "next")
    val next: String? = null,
    @Json(name = "pages")
    val pages: Int,
    @Json(name = "prev")
    val prev: String? = null
)