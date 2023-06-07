package org.sopar.data.remote.request


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FCMToken(
    @field:Json(name = "fcmToken")
    val fcmToken: String
)