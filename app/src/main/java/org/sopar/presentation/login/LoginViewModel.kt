package org.sopar.presentation.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.sopar.domain.entity.NetworkState
import org.sopar.domain.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    //로그인 state
    private val _loginState = MutableLiveData(NetworkState.LOADING)
    val loginState: LiveData<NetworkState> get() = _loginState
    private val _newUserLoginState = MutableLiveData(NetworkState.LOADING)
    val newUserLoginState: LiveData<NetworkState> get() = _newUserLoginState


    private val callback: (OAuthToken?, Throwable?) -> Unit = { oAuthToken, throwable ->
        if (throwable != null) {
            Log.e("error", "카카오계정으로 로그인 실패", throwable)
            _loginState.postValue(NetworkState.FAIL)
        } else if (oAuthToken != null) {
            Log.i("success", "카카오계정으로 로그인 성공 ${oAuthToken.accessToken}")
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    checkNewUser(oAuthToken.accessToken)
                } catch (e: Exception) {
                    _loginState.postValue(NetworkState.FAIL)
                }
            }
        }
    }

    suspend fun loginWithKakao(context: Context) {
        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                Log.e("error", "로그인 실패", error)
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    return@loginWithKakaoTalk
                }
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            } else if (token != null) {
                Log.i("success", "로그인 성공 ${token.accessToken}")

                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        checkNewUser(token.accessToken)
                    } catch (e: Exception) {
                        _loginState.postValue(NetworkState.FAIL)
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            }
        }
    }

    private suspend fun checkNewUser(accessToken: String) {

        val response = authRepository.login(accessToken)

        if (response.isSuccessful) {
            authRepository.saveAccessToken(accessToken)

            response.body()?.jwt?.let {
                authRepository.saveJwt(it)
            }

            response.body()?.userId?.let {
                authRepository.saveUId(it)
            }
            response.body()?.newUser?.let {
                if (it) {
                    _newUserLoginState.postValue(NetworkState.SUCCESS)
                } else {
                    _loginState.postValue(NetworkState.SUCCESS)
                }
            }
        } else {
            _loginState.postValue(NetworkState.FAIL)
        }
    }

    fun checkToken() {
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error != null) {
                    if ((error !is KakaoSdkError) || !error.isInvalidTokenError()) {
                        _loginState.postValue(NetworkState.FAIL)
                    }
                } else {
                    viewModelScope.launch {
                        checkNewUser(authRepository.getAccessToken().first())
                    }
                }
            }
        }
    }
}