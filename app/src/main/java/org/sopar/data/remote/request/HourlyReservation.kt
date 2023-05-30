package org.sopar.data.remote.request


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class HourlyReservation(
    @field:Json(name = "date")
    val date: String,
    @field:Json(name = "duration")
    val duration: List<Int>
): Parcelable