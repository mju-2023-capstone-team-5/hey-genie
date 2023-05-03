package org.sopar.data.api

import org.sopar.data.remote.request.LoginRequest
import org.sopar.data.remote.request.UserRegisterRequest
import org.sopar.data.remote.response.LoginResponse
import org.sopar.data.remote.response.SearchResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitApi {

    @POST("/api/v1/login/token")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @POST("/api/v1/register")
    suspend fun userRegister(
        @Body userRegisterRequest: UserRegisterRequest
    ): Response<String>

    @GET("v2/local/search/keyword.json")
    suspend fun getSearchLocation(
        @Header("Authorization") token: String,
        @Query("query") query: String
    ): Response<SearchResponse>

}