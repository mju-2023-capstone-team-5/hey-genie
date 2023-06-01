package org.sopar.presentation.entry

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.sopar.data.remote.request.FCMToken
import org.sopar.domain.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class EntryViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    fun setUserFCMToken(token: String) {
        viewModelScope.launch {
            try {
                val userId = authRepository.getUId().first()
                authRepository.addFCMToken(userId, FCMToken(token))
            } catch (e: java.lang.Exception) {
                Log.d("addFCMToken Error", e.toString())
            }
        }
    }

}