package org.sopar.data.remote.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class AvailableTime(
    @Json(name = "endMinute")
    val endMinute: Int,
    @Json(name = "endTime")
    val endTime: Int,
    @Json(name = "startMinute")
    val startMinute: Int,
    @Json(name = "startTime")
    val startTime: Int
): Parcelable