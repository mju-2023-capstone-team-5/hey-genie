package org.sopar.presentation.myReservation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.sopar.data.remote.request.Grade
import org.sopar.data.remote.request.Reservation
import org.sopar.data.remote.response.ReservationPreview
import org.sopar.domain.entity.NetworkState
import org.sopar.domain.repository.AuthRepository
import org.sopar.domain.repository.ParkingLotRepository
import java.time.LocalDateTime
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
    private val _registerGradeState = MutableLiveData(NetworkState.LOADING)
    val registerGrade: LiveData<NetworkState> get() = _registerGradeState
    private val _reservation = MutableLiveData<Reservation>()
    val reservation: LiveData<Reservation> get() = _reservation

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

    @RequiresApi(Build.VERSION_CODES.O)
    fun registerParkingLotGrade(reservationId: Int, comment: String?, rating: Float) {
        viewModelScope.launch {
            try {
                val userId = authRepository.getUId().first()
                val reservation = parkingLotRepository.getReservationById(reservationId).body()
                reservation?.let {
                    val grade = Grade(comment ?: null, reservation.parkingLotId, rating, LocalDateTime.now().toString(), userId)
                    Log.d("registerParkingLotGrade Request", grade.toString())
                    val response = parkingLotRepository.registerParkingLotGrade(grade)
                    Log.d("registerParkingLotGrade Response", response.toString())
                    if (response.isSuccessful) {
                        Log.d("registerParkingLotGrade", response.body().toString())
                        _registerGradeState.postValue(NetworkState.SUCCESS)
                    }
                }
            } catch (e: java.lang.Exception) {
                Log.d("registerParkingLotGrade Error", e.toString())
                _registerGradeState.postValue(NetworkState.FAIL)
            }
        }
    }

    fun getReservationById(id: Int) {
        viewModelScope.launch {
            try {
                Log.d("reservation id", id.toString())
                val response = parkingLotRepository.getReservationById(id)
                Log.d("reservation", response.body().toString())
                _reservation.postValue(response.body())
            } catch (e: java.lang.Exception) {
                Log.d("getReservationById Error", e.toString())
                _getReservationState.postValue(NetworkState.FAIL)
            }
        }
    }
}