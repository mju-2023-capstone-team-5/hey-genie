package org.sopar.data.repository

import okhttp3.MultipartBody
import org.sopar.data.api.SoparRetrofitApi
import org.sopar.data.remote.request.Grade
import org.sopar.data.remote.request.ParkingLotRequest
import org.sopar.data.remote.request.Reservation
import org.sopar.data.remote.response.ParkingLot
import org.sopar.data.remote.response.ReservationPreview
import org.sopar.domain.repository.ParkingLotRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ParkingLotRepositoryImpl @Inject constructor(
    private val api: SoparRetrofitApi
): ParkingLotRepository {

    override suspend fun registerParkingLot(parkingLot: ParkingLotRequest): Response<ParkingLot> {
        return api.registerParkingLot(parkingLot)
    }

    override suspend fun registerParkingLotImage(
        id: Int,
        file: MultipartBody.Part
    ): Response<String> {
        return api.registerParkingLotImage(id, file)
    }

    override suspend fun registerPermissionImage(
        id: Int,
        file: List<MultipartBody.Part>
    ): Response<String> {
        return api.registerPermissionImage(id, file)
    }

    override suspend fun registerReservation(reservation: Reservation): Response<Reservation> {
        return api.registerReservation(reservation)
    }

    override suspend fun getParkingLotByUser(id: Int): Response<List<ParkingLot>?> {
        return api.getParkingLotsByUser(id)
    }

    override suspend fun deleteParkingLotById(id: Int): Response<String> {
        return api.deleteParkingLotById(id)
    }

    override suspend fun getReservationByUser(id: Int): Response<List<ReservationPreview>> {
        return api.getReservationByUser(id)
    }

    override suspend fun deleteReservationById(id: Int): Response<String> {
        return api.deleteReservationById(id)
    }

    override suspend fun getReservationById(id: Int): Response<Reservation> {
        return api.getReservationById(id)
    }

    override suspend fun registerParkingLotGrade(grade: Grade): Response<Grade> {
        return api.registerParkingLotGrade(grade)
    }

}