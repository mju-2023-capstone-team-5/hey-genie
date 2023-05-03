package org.sopar.data.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(
    @field:Json(name = "documents")
    val documents: List<Place>,
    @field:Json(name = "meta")
    val meta: Meta
)