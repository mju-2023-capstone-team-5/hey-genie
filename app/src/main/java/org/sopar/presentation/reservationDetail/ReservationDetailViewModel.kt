package org.sopar.presentation.reservationDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.sopar.data.remote.request.Reservation
import org.sopar.domain.entity.NetworkState
import org.sopar.domain.repository.ParkingLotRepository
import javax.inject.Inject

@HiltViewModel
class ReservationDetailViewModel @Inject constructor(
    private val parkingLotRepository: ParkingLotRepository
): ViewModel() {
    private val _getReservationState = MutableLiveData(NetworkState.LOADING)
    val getReservationState: LiveData<NetworkState> get() = _getReservationState
    private val _reservation = MutableLiveData<Reservation>()
    val reservation: LiveData<Reservation> get() = _reservation
    private val _deleteReservationState = MutableLiveData(NetworkState.LOADING)
    val deleteReservationState: LiveData<NetworkState> get() = _deleteReservationState

    fun getReservationById(id: Int) {
        viewModelScope.launch {
            try {
                Log.d("reservaton id", id.toString())
                val response = parkingLotRepository.getReservationById(id)
                Log.d("reservation", response.body().toString())
                _reservation.postValue(response.body())
            } catch (e: java.lang.Exception) {
                Log.d("getReservationById Error", e.toString())
                _getReservationState.postValue(NetworkState.FAIL)
            }
        }
    }

    fun deleteReservationById(id: Int) {
        viewModelScope.launch {
            try {
                val response = parkingLotRepository.deleteReservationById(id)
                if (response.isSuccessful) {
                    _deleteReservationState.postValue(NetworkState.SUCCESS)
                }
            } catch (e: java.lang.Exception) {
                Log.d("deleteReservation Error", e.toString())
                _deleteReservationState.postValue(NetworkState.FAIL)
            }
        }
    }
}