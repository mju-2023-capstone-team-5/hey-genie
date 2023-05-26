package org.sopar.presentation.reservation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.sopar.data.remote.request.HourlyReservationInfo
import org.sopar.data.remote.request.MonthlyReservationInfo
import org.sopar.data.remote.request.Reservation
import org.sopar.data.remote.response.ParkingLot
import org.sopar.domain.entity.NetworkState
import org.sopar.domain.repository.AuthRepository
import org.sopar.domain.repository.MapRepository
import org.sopar.domain.repository.ParkingLotRepository
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ReservationViewModel @Inject constructor(
    private val mapRepository: MapRepository,
    private val authRepository: AuthRepository,
    private val parkingLotRepository: ParkingLotRepository
): ViewModel() {
    private val _getParkingLotState = MutableLiveData(NetworkState.LOADING)
    val getParkingLotState: LiveData<NetworkState> = _getParkingLotState
    private val _parkingLot = MutableLiveData<ParkingLot>()
    val parkingLot: LiveData<ParkingLot> = _parkingLot
    private val _times = MutableLiveData<Set<Int>>(mutableSetOf())
    val times: LiveData<Set<Int>> get() = _times
    private val _reservationStatus = MutableLiveData(NetworkState.LOADING)
    val reservationStatus: LiveData<NetworkState> = _reservationStatus

    fun postTimes(times: MutableSet<Int>) {
        _times.postValue(times)
    }


    fun getParkingLotsById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = mapRepository.getParkingLotsById(id)
                _parkingLot.postValue(response.body())
                Log.e("getParkingLotById", response.body().toString())
            } catch (e: IOException) {
                _getParkingLotState.postValue(NetworkState.FAIL)
            }
        }
    }

    fun setParkingLot(parkingLot: ParkingLot) {
        _parkingLot.postValue(parkingLot)
    }

    fun registerHourlyReservation(parkingLotId: Int, hourlyReservationInfo: HourlyReservationInfo, price: Int) {
        viewModelScope.launch {
            try {
                val userId = authRepository.getUId().first()
                val reservation = Reservation(null, userId, parkingLotId, null, hourlyReservationInfo, price)
                Log.d("registerHourlyReservation", reservation.toString())
                val response = parkingLotRepository.registerReservation(reservation)
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

            } catch (e: java.lang.Exception) {
                Log.d("registerMonthlyReservation error", e.toString())
                _reservationStatus.postValue(NetworkState.FAIL)
            }
        }
    }

}