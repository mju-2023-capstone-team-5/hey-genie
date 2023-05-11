package org.sopar.data.repository

import org.sopar.data.api.SoparRetrofitApi
import org.sopar.data.remote.response.ParkingLot
import org.sopar.domain.repository.MapRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapRepositoryImp @Inject constructor(
    private val api: SoparRetrofitApi
): MapRepository {

    override suspend fun getParkingLots(
        x1: Double,
        y1: Double,
        x2: Double,
        y2: Double
    ): Response<List<ParkingLot>> {
        return api.getParkingLots(x1, y1, x2, y2)
    }

}