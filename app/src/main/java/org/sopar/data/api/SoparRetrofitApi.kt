package org.sopar.data.api

import okhttp3.MultipartBody
import org.sopar.data.remote.request.LoginRequest
import org.sopar.data.remote.request.ParkingLotRequest
import org.sopar.data.remote.request.UserRegisterRequest
import org.sopar.data.remote.response.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface SoparRetrofitApi {

    @POST("/api/v1/login/token")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @POST("/api/v1/register")
    suspend fun userRegister(
        @Body userRegisterRequest: UserRegisterRequest
    ): Response<String>

    @POST("/api/v1/parking-lots")
    suspend fun registerParkingLot(
        @Body parkingLotRequest: ParkingLotRequest
    ): Response<ParkingLot>

    @Multipart
    @POST("/api/v1/parking-lots/{id}/images/info")
    suspend fun registerParkingLotImage(
        @Path("id") id: Int,
        @Part file: MultipartBody.Part?
    ): Response<String>

    @Multipart
    @POST("/api/v1/parking-lots/{id}/images/permit-request")
    suspend fun registerPermissionImage(
        @Path("id") id: Int,
        @Part file: List<MultipartBody.Part>
    ): Response<String>

}