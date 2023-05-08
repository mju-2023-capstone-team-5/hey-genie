package org.sopar.data.remote.request

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Reservation(
    @field:Json(name = "reservationId")
    val reservationId: Int,
    @field:Json(name = "userId")
    val userId: Int,
    @field:Json(name = "parkingLotId")
    val parkingLotId: Int,
    @field:Json(name = "monthlyReservationInfo")
    val monthlyReservationInfo: MonthlyReservationInfo?,
    @field:Json(name = "hourlyReservationInfo")
    val hourlyReservationInfo: HourlyReservationInfo?,
    @field:Json(name = "price")
    val price: Int
): Parcelable
