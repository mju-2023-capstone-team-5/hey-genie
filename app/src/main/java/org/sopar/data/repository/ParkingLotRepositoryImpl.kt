package org.sopar.data.repository

import org.sopar.data.api.SoparRetrofitApi
import org.sopar.data.remote.request.ParkingLotRequest
import org.sopar.data.remote.response.ParkingLot
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

}