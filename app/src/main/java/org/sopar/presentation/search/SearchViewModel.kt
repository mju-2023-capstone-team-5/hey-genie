package org.sopar.presentation.search

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
import org.sopar.data.remote.response.SearchResponse
import org.sopar.domain.entity.NetworkState
import org.sopar.domain.repository.KakaoRepository
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val kakaoRepository: KakaoRepository
): ViewModel() {
    private val _searchState = MutableLiveData(NetworkState.LOADING)
    val searchState: LiveData<NetworkState> get() = _searchState
    private val _searchResult = MutableLiveData<List<Place>>(listOf())
    val searchResult: LiveData<List<Place>> get() = _searchResult

    fun getSearchLocation(query: String) {
        viewModelScope.launch(Dispatchers.IO){
            try {
                val token = "KakaoAK ${BuildConfig.RESTAPI_KEY}"
                val response = kakaoRepository.getSearchLocation(token, query)
                _searchResult.postValue(response.body()?.documents)
            } catch (e: Exception) {
                _searchState.postValue(NetworkState.FAIL)
            }
        }
    }
}