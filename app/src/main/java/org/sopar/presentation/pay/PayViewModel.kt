package org.sopar.presentation.pay

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.sopar.data.remote.request.*
import org.sopar.domain.entity.NetworkState
import org.sopar.domain.entity.ParkingLotState
import org.sopar.domain.repository.AuthRepository
import org.sopar.domain.repository.ParkingLotRepository
import javax.inject.Inject

@HiltViewModel
class PayViewModel @Inject constructor(
    private val parkingLotRepository: ParkingLotRepository,
    private val authRepository: AuthRepository
): ViewModel() {
    private val _reservationState = MutableLiveData(NetworkState.LOADING)
    val reservationState: LiveData<NetworkState> = _reservationState
    private val _parkingLotState = MutableLiveData(ParkingLotState.POSSIBLE)
    val parkingLotState: LiveData<ParkingLotState> = _parkingLotState

    fun registerHourlyReservation(parkingLotId: Int, hourlyReservationInfo: HourlyReservation, price: Int) {
        viewModelScope.launch {
            try {
                val userId = authRepository.getUId().first()
                val reservation = Reservation(hourlyReservationInfo, null, false, parkingLotId, price, userId)
                val response = parkingLotRepository.registerReservation(reservation)
                Log.d("registerHourlyReservation request", reservation.toString())
                if (response.code() == 200) {
                    Log.d("registerHourlyReservation", response.body().toString())
                    _reservationState.postValue(NetworkState.SUCCESS)
                } else if (response.code() == 503) {
                    _parkingLotState.postValue(ParkingLotState.IMPOSSIBLE)
                }else {
                    Log.d("registerHourlyReservation", response.code().toString())
                    Log.d("registerHourlyReservation", response.message().toString())
                    _reservationState.postValue(NetworkState.FAIL)
                }
            } catch (e: java.lang.Exception) {
                Log.d("registerHourlyReservation error", e.toString())
                _reservationState.postValue(NetworkState.FAIL)
            }
        }
    }

    fun registerMonthlyReservation(parkingLotId: Int, monthlyReservationInfo: MonthlyReservation, price: Int) {
        viewModelScope.launch {
            try {
                val userId = authRepository.getUId().first()
                val reservation = Reservation(null, monthlyReservationInfo, false, parkingLotId, price, userId)
                val response = parkingLotRepository.registerReservation(reservation)
                Log.d("registerHourlyReservation request", reservation.toString())
                if (response.code() == 200) {
                    Log.d("registerMonthlyReservation", response.body().toString())
                    _reservationState.postValue(NetworkState.SUCCESS)
                } else if (response.code() == 503) {
                    _parkingLotState.postValue(ParkingLotState.IMPOSSIBLE)
                } else {
                    Log.d("registerMonthlyReservation", response.code().toString())
                    Log.d("registerMonthlyReservation", response.message().toString())
                    _reservationState.postValue(NetworkState.FAIL)
                }
            } catch (e: java.lang.Exception) {
                Log.d("registerMonthlyReservation error", e.toString())
                _reservationState.postValue(NetworkState.FAIL)
            }
        }
    }
}