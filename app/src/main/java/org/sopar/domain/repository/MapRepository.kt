package org.sopar.domain.repository

import org.sopar.data.remote.response.ParkingLot
import retrofit2.Response

interface MapRepository {
    suspend fun getParkingLots(x1: Double, y1: Double, x2: Double, y2: Double): Response<List<ParkingLot>>

    suspend fun getParkingLotsById(id: Int): Response<ParkingLot>

}