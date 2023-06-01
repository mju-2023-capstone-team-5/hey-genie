package org.sopar.data.remote.request


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Grade(
    @field:Json(name = "comment")
    val comment: String?,
    @field:Json(name = "parkingLotId")
    val parkingLotId: Int,
    @field:Json(name = "reservationId")
    val reservationId: Int,
    @field:Json(name = "rating")
    val rating: Float,
    @field:Json(name = "timestamp")
    val timestamp: String,
    @field:Json(name = "userId")
    val userId: Int
)