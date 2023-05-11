package org.sopar.presentation.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    fun getParkingLots(x1: Double, y1: Double, x2: Double, y2: Double){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                //목 데이터
                val response = mapRepository.getParkingLots(0.0, 0.0, 0.0, 0.0)
                //실제 사용할 부분
                //val response = mapRepository.getParkingLots(x1, y1, x2, y2)
                _parkingLots.postValue(response.body())
                _getParkingLotState.postValue(NetworkState.SUCCESS)
            } catch (e: IOException) {
                _getParkingLotState.postValue(NetworkState.FAIL)
            }
        }
    }

}