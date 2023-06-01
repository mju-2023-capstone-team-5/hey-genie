package org.sopar.data.remote.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInfo(
    @field:Json(name = "address")
    val address: String,
    @field:Json(name = "carNumber")
    val carNumber: String,
    @field:Json(name = "email")
    val email: String,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "phone")
    val phone: String,
    @field:Json(name = "points")
    val points: Int
)