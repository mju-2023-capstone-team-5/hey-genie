package org.sopar.data.api

import com.squareup.moshi.Json
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.sopar.data.remote.request.LoginRequest
import org.sopar.data.remote.request.ParkingLotRequest
import org.sopar.data.remote.request.UserRegisterRequest
import org.sopar.data.remote.response.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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

    @POST("/api/v1/parking-lots")
    suspend fun registerParkingLot(
        @Body parkingLotRequest: ParkingLotRequest
    ): Response<ParkingLot>

}