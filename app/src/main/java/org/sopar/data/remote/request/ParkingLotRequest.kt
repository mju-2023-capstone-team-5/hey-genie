package org.sopar.data.remote.request

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import org.sopar.data.remote.response.Rate
import org.sopar.data.remote.response.TimeSlot

@JsonClass(generateAdapter = true)
@Parcelize
data class ParkingLotRequest(
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "address")
    val address: String,
    @field:Json(name = "availableDay")
    val availableDay: List<String>,
    @field:Json(name = "availableTime")
    val availableTime: List<TimeSlot>,
    @field:Json(name = "freeInformation")
    val freeInformation: String?,
    @field:Json(name = "hourly")
    val hourly: Rate?,
    @field:Json(name = "latitude")
    val latitude: Double,
    @field:Json(name = "longitude")
    val longitude: Double,
    @field:Json(name = "monthly")
    val monthly: Rate?,
    @field:Json(name = "ownerId")
    var ownerId: Int?,
    @field:Json(name = "phoneNumber")
    val phoneNumber: String,
    @field:Json(name = "remainingSpace")
    val remainingSpace: Int,
    @field:Json(name = "totalSpace")
    val totalSpace: Int,
    @field:Json(name = "type")
    val type: List<String>
): Parcelable