package org.sopar.data.remote.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ParkingLot(
    @field:Json(name = "address")
    val address: String,
    @field:Json(name = "imageUrl")
    val imageUrl: String?,
    @field:Json(name = "latitude")
    val latitude: Double,
    @field:Json(name = "longitude")
    val longitude: Double,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "parkingLotId")
    val parkingLotId: Int,
    @field:Json(name = "remainingSpace")
    val remainingSpace: Int?,
    @field:Json(name = "minimum")
    val minimum: Int?,
    @field:Json(name = "surcharge")
    val surcharge: Int?
): Parcelable