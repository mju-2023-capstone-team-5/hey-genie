package org.sopar.presentation.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapPoint
import org.sopar.data.remote.response.ParkingLot
import org.sopar.domain.entity.NetworkState
import org.sopar.domain.repository.MapRepository
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val mapRepository: MapRepository
): ViewModel() {
    private val _getParkingLotState = MutableLiveData(NetworkState.LOADING)
    val getParkingLotState: LiveData<NetworkState> = _getParkingLotState
    private val _parkingLots = MutableLiveData<List<ParkingLot>>(listOf())
    val parkingLots: LiveData<List<ParkingLot>> = _parkingLots
    private val _mapCenter = MutableLiveData(MapPoint.mapPointWithGeoCoord(-1.0E7, -1.0E7))
    val mapCenter: LiveData<MapPoint> = _mapCenter

    fun getParkingLots(x1: Double, x2: Double, y1: Double, y2: Double){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = mapRepository.getParkingLots(x1, y1, x2, y2)
                Log.e("map viewmodel", response.body().toString())
                _parkingLots.postValue(response.body())
                _getParkingLotState.postValue(NetworkState.SUCCESS)
            } catch (e: IOException) {
                _getParkingLotState.postValue(NetworkState.FAIL)
            }
        }
    }

    fun updateMapCenter(mapPoint: MapPoint?) {
        _mapCenter.postValue(mapPoint)
    }

}