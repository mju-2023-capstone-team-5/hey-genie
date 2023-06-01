package org.sopar.domain.repository

import okhttp3.MultipartBody
import org.sopar.data.remote.request.Grade
import org.sopar.data.remote.request.ParkingLotRequest
import org.sopar.data.remote.request.Reservation
import org.sopar.data.remote.response.ParkingLot
import org.sopar.data.remote.response.ReservationPreview
import org.sopar.data.remote.response.UserInfo
import retrofit2.Response

interface ParkingLotRepository {
    suspend fun registerParkingLot(parkingLot: ParkingLotRequest): Response<ParkingLot>

    suspend fun registerParkingLotImage(id: Int, file: MultipartBody.Part): Response<String>

    suspend fun registerPermissionImage(id: Int, file: List<MultipartBody.Part>): Response<String>

    suspend fun registerReservation(reservation: Reservation): Response<Reservation>

    suspend fun getParkingLotByUser(id: Int): Response<List<ParkingLot>?>

    suspend fun deleteParkingLotById(id: Int): Response<String>

    suspend fun getReservationByUser(id: Int): Response<List<ReservationPreview>>

    suspend fun deleteReservationById(id: Int): Response<String>

    suspend fun getReservationById(id: Int): Response<Reservation>

    suspend fun registerParkingLotGrade(grade: Grade): Response<Grade>

    suspend fun getUserInfoById(id: Int): Response<UserInfo>

    suspend fun getCommentByPKId(id: Int): Response<List<Grade?>>

}