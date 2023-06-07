package org.sopar.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.sopar.data.api.SoparRetrofitApi
import org.sopar.data.remote.request.FCMToken
import org.sopar.data.remote.request.LoginRequest
import org.sopar.data.remote.request.UserRegisterRequest
import org.sopar.data.remote.response.LoginResponse
import org.sopar.data.repository.AuthRepositoryImpl.PreferencesKeys.ACCESS_TOKEN_KEY
import org.sopar.data.repository.AuthRepositoryImpl.PreferencesKeys.JWT_KEY
import org.sopar.data.repository.AuthRepositoryImpl.PreferencesKeys.UID_Key
import org.sopar.domain.repository.AuthRepository
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val api: SoparRetrofitApi,
    private val dataStore: DataStore<Preferences>
) : AuthRepository {
    override suspend fun login(accessToken: String): Response<LoginResponse> {
        val loginRequest = LoginRequest(accessToken)
        return api.login(loginRequest)
    }

    override suspend fun saveJwt(jwt: String) {
        dataStore.edit { prefs ->
            prefs[JWT_KEY] = jwt
        }
    }

    override suspend fun getJwt(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { prefs ->
                prefs[JWT_KEY] ?: ""
            }
    }

    override suspend fun saveUId(id: Int) {
        dataStore.edit { prefs ->
            prefs[UID_Key] = id
        }
    }

    override suspend fun getUId(): Flow<Int> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { prefs ->
                prefs[UID_Key] ?: -1
            }
    }

    override suspend fun userRegister(userRegisterRequest: UserRegisterRequest): Response<String> {
        return api.userRegister(userRegisterRequest)
    }

    override suspend fun saveAccessToken(accessToken: String) {
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN_KEY] = accessToken
        }
    }

    override suspend fun getAccessToken(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    exception.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { prefs ->
                prefs[ACCESS_TOKEN_KEY] ?: ""
            }
    }

    override suspend fun addFCMToken(userId: Int, fcmToken: FCMToken): Response<String> {
        return api.addFCMToken(userId, fcmToken)
    }

    private object PreferencesKeys {
        // jwt
        val JWT_KEY: Preferences.Key<String> = stringPreferencesKey("jwt_key")
        // uid
        val UID_Key: Preferences.Key<Int> = intPreferencesKey("uid_key")
        //accessToken
        val ACCESS_TOKEN_KEY: Preferences.Key<String> = stringPreferencesKey("access_token_key")
    }
}