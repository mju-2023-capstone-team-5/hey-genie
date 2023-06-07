package org.sopar.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sopar.data.remote.request.FCMToken
import org.sopar.data.remote.request.UserRegisterRequest
import org.sopar.data.remote.response.LoginResponse
import retrofit2.Response

interface AuthRepository {
    suspend fun login(accessToken: String): Response<LoginResponse>

    suspend fun saveJwt(jwt: String)
    suspend fun getJwt(): Flow<String>

    suspend fun saveUId(id: Int)
    suspend fun getUId(): Flow<Int>

    suspend fun saveAccessToken(accessToken: String)
    suspend fun getAccessToken(): Flow<String>

    suspend fun userRegister(userRegisterRequest: UserRegisterRequest): Response<String>

    suspend fun addFCMToken(userId: Int, fcmToken: FCMToken): Response<String>
}