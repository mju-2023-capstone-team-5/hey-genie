package org.sopar.data.remote.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Rate(
    @field:Json(name = "minimum")
    val minimum: Int,
    @field:Json(name = "surcharge")
    val surcharge: Int
): Parcelable