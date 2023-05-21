package org.sopar.presentation.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.sopar.BuildConfig
import org.sopar.data.remote.response.Place
import org.sopar.data.remote.response.Rate
import org.sopar.data.remote.response.TimeSlot
import org.sopar.domain.entity.NetworkState
import org.sopar.domain.repository.AuthRepository
import org.sopar.domain.repository.KakaoRepository
import org.sopar.domain.repository.ParkingLotRepository
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
   private val authRepository: AuthRepository,
   private val parkingLotRepository: ParkingLotRepository,
   private val kakaoRepository: KakaoRepository,
): ViewModel() {
    var name: String? = null
    var address: String? = null
    var freeInformation: String? = null
    var phoneNumber: String? = null
    var latitude: Double? = null
    var longitude: Double? = null
    var totalSpace: Int? = null
    val remainingSpace: Int? = null
    val ownerId: Int? = null
    val minimum: Int? = null
    val surcharge: Int? = null
    var type: List<String>? = null
    var availableDay: List<String>? = null
    var availableTime: List<TimeSlot>? = null
    val monthly: Rate? = null
    var hourly: Rate? = null

    var imageUrl: String? = null
    var permissionUrl: String? = null

    private val _searchResult = MutableLiveData<List<Place>>(listOf())
    val searchResult: LiveData<List<Place>> get() = _searchResult

    fun getSearchLocation(query: String) {
        viewModelScope.launch(Dispatchers.IO){
            try {
                val token = "KakaoAK ${BuildConfig.RESTAPI_KEY}"
                val response = kakaoRepository.getSearchLocation(token, query)
                _searchResult.postValue(response.body()?.documents)
            } catch (e: Exception) {
            }
        }
    }
}