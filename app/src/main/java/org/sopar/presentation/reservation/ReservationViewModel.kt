package org.sopar.presentation.reservation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.sopar.data.remote.request.Grade
import org.sopar.data.remote.response.ParkingLot
import org.sopar.domain.entity.NetworkState
import org.sopar.domain.repository.MapRepository
import org.sopar.domain.repository.ParkingLotRepository
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ReservationViewModel @Inject constructor(
    private val mapRepository: MapRepository,
    private val parkingLotRepository: ParkingLotRepository
): ViewModel() {
    private val _getParkingLotState = MutableLiveData(NetworkState.LOADING)
    val getParkingLotState: LiveData<NetworkState> = _getParkingLotState
    private val _parkingLot = MutableLiveData<ParkingLot>()
    val parkingLot: LiveData<ParkingLot> = _parkingLot
    private val _times = MutableLiveData<Set<Int>>(mutableSetOf())
    val times: LiveData<Set<Int>> get() = _times
    private val _commentList = MutableLiveData<List<Grade?>>()
    val commentList: LiveData<List<Grade?>> get() = _commentList
    private val _getCommentState = MutableLiveData(NetworkState.LOADING)
    val getCommentState: LiveData<NetworkState> get() = _getCommentState

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

    fun getCommentByPKId(parkingLotId: Int) {
        viewModelScope.launch {
            try {
                val response = parkingLotRepository.getCommentByPKId(parkingLotId)
                Log.d("getCommentByPKId", response.body().toString())
                _commentList.postValue(response.body())
            } catch (e: java.lang.Exception) {
                Log.d("getCommentByPKId Error", e.toString())
                _getCommentState.postValue(NetworkState.FAIL)
            }
        }
    }

}