package org.sopar.presentation.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient

class LoginViewModel: ViewModel() {
    private val _error = MutableLiveData(false)
    val error: LiveData<Boolean> get() = _error

    private val callback: (OAuthToken?, Throwable?) -> Unit = { oAuthToken, throwable ->
        if (throwable != null) {
            Log.e("error", "카카오계정으로 로그인 실패", throwable)
            _error.postValue(true)
        } else if (oAuthToken != null) {
            Log.i("success", "카카오계정으로 로그인 성공 ${oAuthToken.accessToken}")
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
                //서버에 유저 확인
            } else {
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            }
        }
    }

    suspend fun checkToken() {
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error != null) {
                    if ((error !is KakaoSdkError) || !error.isInvalidTokenError()) {
                        _error.postValue(true)
                    }
                } else {
                    //로그인 처리 진행
                }
            }
        }
    }
}