package org.sopar.data.api

import org.sopar.data.remote.request.LoginRequest
import org.sopar.data.remote.request.UserRegisterRequest
import org.sopar.data.remote.response.LoginResponse
import org.sopar.data.remote.response.ParkingLot
import org.sopar.data.remote.response.SearchResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SoparRetrofitApi {

    @POST("/api/v1/login/token")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @POST("/api/v1/register")
    suspend fun userRegister(
        @Body userRegisterRequest: UserRegisterRequest
    ): Response<String>

    @GET("/api/v1/parking-lots/rectangle")
    suspend fun getParkingLots(
      @Query("x1") x1: Double,
      @Query("y1") y1: Double,
      @Query("x2") x2: Double,
      @Query("y2") y2: Double
    ): Response<List<ParkingLot>>

    @GET("/api/v1/parking-lots/{id}")
    suspend fun getParkingLotsById(
        @Path("id") id: Int
    ): Response<ParkingLot>
}