package org.sopar.presentation.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.sopar.data.remote.response.UserInfo
import org.sopar.domain.repository.AuthRepository
import org.sopar.domain.repository.ParkingLotRepository
import javax.inject.Inject

@HiltViewModel
class MainVIewModel @Inject constructor(
    private val parkingLotRepository: ParkingLotRepository,
    private val authRepository: AuthRepository
): ViewModel() {
    private val _userInfo = MutableLiveData<UserInfo> (UserInfo("", "", "", "", "", 0))
    val userInfo: LiveData<UserInfo> get() = _userInfo

    fun getUserInfoById() {
        viewModelScope.launch {
            try {
                val userId = authRepository.getUId().first()
                val response = parkingLotRepository.getUserInfoById(userId)
                Log.d("getUserInfoById", response.body().toString())
                _userInfo.postValue(response.body())
            } catch (e: java.lang.Exception) {
                Log.d("getUserInfoById Error", e.toString())
            }
        }
    }

}