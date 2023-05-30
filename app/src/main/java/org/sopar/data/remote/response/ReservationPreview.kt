package org.sopar.data.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReservationPreview(
    @field:Json(name = "endTime")
    val endTime: String,
    @field:Json(name = "parkingLotName")
    val parkingLotName: String,
    @field:Json(name = "reservationId")
    val reservationId: Int,
    @field:Json(name = "startTime")
    val startTime: String
)