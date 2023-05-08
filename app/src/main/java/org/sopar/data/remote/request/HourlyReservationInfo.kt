package org.sopar.data.remote.request

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.util.Date

@JsonClass(generateAdapter = true)
@Parcelize
data class HourlyReservationInfo(
    @field:Json(name = "date")
    val date: Date,
    @field:Json(name = "startHour")
    val startHour: Int,
    @field:Json(name = "startMinute")
    val startMinute: Int,
    @field:Json(name = "duration")
    val duration: Int
): Parcelable
