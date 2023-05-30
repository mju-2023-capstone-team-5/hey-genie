package org.sopar.data.remote.request


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Reservation(
    @field:Json(name = "hourlyReservation")
    val hourlyReservation: HourlyReservation?,
    @field:Json(name = "monthlyReservation")
    val monthlyReservation: MonthlyReservation?,
    @field:Json(name = "parkingLotId")
    val parkingLotId: Int,
    @field:Json(name = "price")
    val price: Int,
    @field:Json(name = "userId")
    val userId: Int
): Parcelable