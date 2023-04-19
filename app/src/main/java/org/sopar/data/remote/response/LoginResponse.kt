package org.sopar.data.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
    @field:Json(name = "jwt") val jwt: String?,
    @field:Json(name = "userId") val userId: Int?,
    @field:Json(name = "newUser") val newUser: Boolean?
)
