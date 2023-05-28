package org.sopar.presentation.pay

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.sopar.data.remote.request.HourlyReservationInfo
import org.sopar.data.remote.request.MonthlyReservationInfo
import org.sopar.data.remote.request.Reservation
import org.sopar.domain.entity.NetworkState
import org.sopar.domain.repository.AuthRepository
import org.sopar.domain.repository.ParkingLotRepository
import javax.inject.Inject

@HiltViewModel
class PayViewModel @Inject constructor(
    private val parkingLotRepository: ParkingLotRepository,
    private val authRepository: AuthRepository
): ViewModel() {
    private val _reservationStatus = MutableLiveData(NetworkState.LOADING)
    val reservationStatus: LiveData<NetworkState> = _reservationStatus

    fun registerHourlyReservation(parkingLotId: Int, hourlyReservationInfo: HourlyReservationInfo, price: Int) {
        viewModelScope.launch {
            try {
                val userId = authRepository.getUId().first()
                val reservation = Reservation(null, userId, parkingLotId, null, hourlyReservationInfo, price)
                Log.d("registerHourlyReservation", reservation.toString())
                val response = parkingLotRepository.registerReservation(reservation)
                Log.d("registerHourlyReservation", response.body().toString())
                _reservationStatus.postValue(NetworkState.SUCCESS)
            } catch (e: java.lang.Exception) {
                Log.d("registerHourlyReservation error", e.toString())
                _reservationStatus.postValue(NetworkState.FAIL)
            }
        }
    }

    fun registerMonthlyReservation(parkingLotId: Int, monthlyReservationInfo: MonthlyReservationInfo, price: Int) {
        viewModelScope.launch {
            try {
                val userId = authRepository.getUId().first()
                val reservation = Reservation(null, userId, parkingLotId, monthlyReservationInfo, null, price)
                Log.d("registerMonthlyReservation", reservation.toString())
                val response = parkingLotRepository.registerReservation(reservation)
                Log.d("registerMonthlyReservation", response.body().toString())
                _reservationStatus.postValue(NetworkState.SUCCESS)
            } catch (e: java.lang.Exception) {
                Log.d("registerMonthlyReservation error", e.toString())
                _reservationStatus.postValue(NetworkState.FAIL)
            }
        }
    }
}