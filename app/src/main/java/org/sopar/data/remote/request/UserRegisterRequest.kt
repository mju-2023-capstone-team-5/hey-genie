package org.sopar.data.remote.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserRegisterRequest(
    @field:Json(name = "userId") val userId: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "address") val address: String,
    @field:Json(name = "phone") val phone: String,
    @field:Json(name = "carNumber") val carNumber: String
)
