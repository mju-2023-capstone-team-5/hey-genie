package org.sopar.presentation.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.sopar.BuildConfig
import org.sopar.data.remote.request.ParkingLotRequest
import org.sopar.data.remote.response.ParkingLot
import org.sopar.data.remote.response.Place
import org.sopar.data.remote.response.Rate
import org.sopar.data.remote.response.TimeSlot
import org.sopar.domain.entity.NetworkState
import org.sopar.domain.repository.AuthRepository
import org.sopar.domain.repository.KakaoRepository
import org.sopar.domain.repository.ParkingLotRepository
import retrofit2.Response
import java.io.File
import java.lang.Exception
import java.time.LocalDate
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
    val ownerId: Int? = null
    var type: List<String>? = null
    var availableDay: List<String>? = null
    var availableTime: List<TimeSlot>? = null
    var monthly: Rate? = null
    var hourly: Rate? = null

    var imageUrl: String? = null
    var permissionUrl: String? = null

    private val _searchResult = MutableLiveData<List<Place>>(listOf())
    val searchResult: LiveData<List<Place>> get() = _searchResult

    private val _registerPKState = MutableLiveData(NetworkState.LOADING)
    val registerPKState: LiveData<NetworkState> get() = _registerPKState
    private val _registerImageState = MutableLiveData(NetworkState.LOADING)
    val registerImageState: LiveData<NetworkState> get() = _registerImageState

    private var _parkingLotId: Int? = null
    private val parkingLotId: Int get() = _parkingLotId!!

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

    fun registerParkingLot(parkingLotRequest: ParkingLotRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userId = authRepository.getUId().first()
                parkingLotRequest.ownerId = userId
                Log.d("parkingLot", parkingLotRequest.toString())
                val response: Response<ParkingLot> = parkingLotRepository.registerParkingLot(parkingLotRequest)
                if (response.isSuccessful) {
                    Log.d("register parkingLot result", response.body().toString())
                    _parkingLotId = response.body()!!.id
                    _registerPKState.postValue(NetworkState.SUCCESS)
                } else {
                    Log.d("register parkingLot result", response.code().toString())
                    _registerPKState.postValue(NetworkState.FAIL)
                }

            } catch (e: Exception) {
                Log.d("register parkingLot error", e.toString())
                _registerPKState.postValue(NetworkState.FAIL)
            }
        }
    }

    fun registerParkingLotImage(image: File) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var imageRequestBody: RequestBody?
                var imageMultipartBody: MultipartBody.Part? = null
                image?.let {
                    imageRequestBody = image.asRequestBody("image/jpeg".toMediaType())
                    imageMultipartBody = MultipartBody.Part.createFormData(name= "file", filename = image.name, body = imageRequestBody!!)
                }

                val response = parkingLotRepository.registerParkingLotImage(parkingLotId, imageMultipartBody!!)
                _registerImageState.postValue(NetworkState.SUCCESS)

            } catch (e: Exception) {
                Log.d("register parkingLot image", e.toString())
                _registerImageState.postValue(NetworkState.SUCCESS)
            }
        }
    }

    fun registerPermissionImage(image: File) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var imageRequestBody: RequestBody?
                var imageMultipartBody: MultipartBody.Part? = null
                image?.let {
                    imageRequestBody = image.asRequestBody("image/jpeg".toMediaType())
                    imageMultipartBody = MultipartBody.Part.createFormData(name= "file", filename = image.name, body = imageRequestBody!!)
                }

                val response = parkingLotRepository.registerPermissionImage(parkingLotId, listOf(imageMultipartBody!!))
                _registerImageState.postValue(NetworkState.SUCCESS)

            } catch (e: Exception) {
                Log.d("register permission image", e.toString())
                _registerImageState.postValue(NetworkState.SUCCESS)
            }
        }
    }


}