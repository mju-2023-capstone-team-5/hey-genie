package org.sopar.data.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SameName(
    @field:Json(name = "keyword")
    val keyword: String,
    @field:Json(name = "region")
    val region: List<Any>,
    @field:Json(name = "selected_region")
    val selectedRegion: String
)