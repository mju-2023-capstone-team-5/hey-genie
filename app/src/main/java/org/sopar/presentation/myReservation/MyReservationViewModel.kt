package org.sopar.presentation.myReservation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.sopar.data.remote.response.ReservationPreview
import org.sopar.domain.entity.NetworkState
import org.sopar.domain.repository.AuthRepository
import org.sopar.domain.repository.ParkingLotRepository
import javax.inject.Inject

@HiltViewModel
class MyReservationViewModel @Inject() constructor(
    private val parkingLotRepository: ParkingLotRepository,
    private val authRepository: AuthRepository
): ViewModel(){
    private val _getReservationState = MutableLiveData(NetworkState.LOADING)
    val getReservationState: LiveData<NetworkState> get() = _getReservationState
    private val _reservations = MutableLiveData<List<ReservationPreview>?>()
    val reservations: LiveData<List<ReservationPreview>?>get() = _reservations

    fun getReservationByUser() {
        viewModelScope.launch {
            try {
                val userId = authRepository.getUId().first()
                val response = parkingLotRepository.getReservationByUser(userId)
                Log.d("getReservatioByUser", response.body().toString())
                _reservations.postValue(response.body())
            } catch (e: java.lang.Exception) {
                Log.d("getReservationByUser Error", e.toString())
                _getReservationState.postValue(NetworkState.FAIL)
            }
        }
    }
}