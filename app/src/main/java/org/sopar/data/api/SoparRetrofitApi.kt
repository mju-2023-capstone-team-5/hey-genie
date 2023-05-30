package org.sopar.data.api

import okhttp3.MultipartBody
import org.sopar.data.remote.request.LoginRequest
import org.sopar.data.remote.request.ParkingLotRequest
import org.sopar.data.remote.request.Reservation
import org.sopar.data.remote.request.UserRegisterRequest
import org.sopar.data.remote.response.*
import org.sopar.data.remote.response.LoginResponse
import org.sopar.data.remote.response.ParkingLot
import retrofit2.Response
import retrofit2.http.*

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

    @POST("/api/v1/reservation")
    suspend fun registerReservation(
        @Body reservation: Reservation
    ): Response<Reservation>

    @GET("/api/v1/users/{id}/parking-lots")
    suspend fun getParkingLotsByUser(
        @Path("id") id: Int
    ): Response<List<ParkingLot>?>

    @DELETE("/api/v1/parking-lots/{id}")
    suspend fun deleteParkingLotById(
        @Path("id") id: Int
    ): Response<String>

    @GET("/api/v1/users/{id}/parking-lots/history")
    suspend fun getReservationByUser(
        @Path("id") id: Int
    ): Response<List<ReservationPreview>>

    @DELETE("/api/v1/reservation/{id}")
    suspend fun deleteReservationById(
        @Path("id") id: Int
    ): Response<String>

}