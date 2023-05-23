package org.sopar.data.remote.response


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Hourly(
    @Json(name = "minimum")
    val minimum: Int,
    @Json(name = "surcharge")
    val surcharge: Int
):Parcelable