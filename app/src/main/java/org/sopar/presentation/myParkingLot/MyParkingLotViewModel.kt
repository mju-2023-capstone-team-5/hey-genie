package org.sopar.presentation.myParkingLot

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.sopar.data.remote.response.ParkingLot
import org.sopar.domain.entity.NetworkState
import org.sopar.domain.repository.AuthRepository
import org.sopar.domain.repository.ParkingLotRepository
import javax.inject.Inject

@HiltViewModel
class MyParkingLotViewModel @Inject constructor(
    private val parkingLotRepository: ParkingLotRepository,
    private val authRepository: AuthRepository
): ViewModel() {
    private val _parkingLots = MutableLiveData<List<ParkingLot>?>(listOf<ParkingLot>())
    val parkingLots: LiveData<List<ParkingLot>?> get() = _parkingLots
    private val _getParkingLotStatus = MutableLiveData(NetworkState.LOADING)
    val getParkingLotStatus: LiveData<NetworkState> get() = _getParkingLotStatus

    fun getParkingLotByUserId() {
        viewModelScope.launch {
            try {
                val userId = authRepository.getUId().first()
                val response = parkingLotRepository.getParkingLotByUser(userId)
                Log.d("getParkingLotByUserId", response.body().toString())
                _parkingLots.postValue(response.body())
            } catch (e: java.lang.Exception) {
                Log.d("getParkingLotByUserId", e.toString())
                _getParkingLotStatus.postValue(NetworkState.FAIL)
            }
        }

    }
}