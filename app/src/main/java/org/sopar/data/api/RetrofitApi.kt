package org.sopar.data.api

import org.sopar.data.remote.request.LoginRequest
import org.sopar.data.remote.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitApi {

    @POST("/api/v1/login/token")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

}