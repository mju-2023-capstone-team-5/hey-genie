package org.sopar.presentation.parkingLotDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.sopar.domain.entity.NetworkState
import org.sopar.domain.repository.ParkingLotRepository
import javax.inject.Inject

@HiltViewModel
class ParkingLotDetailViewModel @Inject constructor(
    private val parkingLotRepository: ParkingLotRepository
): ViewModel() {
    private val _deleteStatus = MutableLiveData(NetworkState.LOADING)
    val deleteStatus: LiveData<NetworkState> get() = _deleteStatus

    fun deleteParkingLot(id: Int) {
        viewModelScope.launch {
            try {
                val response = parkingLotRepository.deleteParkingLotById(id)
                if (response.isSuccessful) {
                    Log.d("deleteParkingLot", response.code().toString())
                    _deleteStatus.postValue(NetworkState.SUCCESS)
                }
            } catch (e: Exception) {
                Log.d("deleteParkingLot", e.toString())
                _deleteStatus.postValue(NetworkState.FAIL)
            }
        }
    }
}