package org.sopar.data.remote.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class TimeSlot(
    @field:Json(name = "startTime")
    val startTime: Int,
    @field:Json(name = "startMinute")
    val startMinute: Int,
    @field:Json(name = "endTime")
    val endTime: Int,
    @field:Json(name = "endMinute")
    val endMinute: Int
): Parcelable