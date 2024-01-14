package com.example.flowsyroomfrancisco.ui.inicio.olvide

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowsyroomfrancisco.domain.usecases.ChangePasswordUsecase
import com.example.flowsyroomfrancisco.utils.NetworkResultt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OlvideViewModel @Inject constructor(
    private val changePasswordUsecase: ChangePasswordUsecase
) : ViewModel() {

    private val _uiState: MutableStateFlow<OlvideState> by lazy {
        MutableStateFlow(OlvideState())
    }
    val uiState: StateFlow<OlvideState> = _uiState

    private val _uiError = Channel<String>()
    val uiError = _uiError.receiveAsFlow()

    fun handleEvent(event: OlvideEvent) {
        when (event) {
            is OlvideEvent.ChangerPassword -> changePassword(
                event.email,
                event.oldPassword,
                event.newPassword
            )
        }
    }

    private fun changePassword(email: String, oldPassword: String, newPassword: String) {
        viewModelScope.launch {
            changePasswordUsecase.invoke(email, oldPassword, newPassword)
                .catch(action = { cause ->
                    _uiError.send(extractErrorMessage(cause.message ?: ""))
                })
                .collect { result ->
                    when (result) {
                        is NetworkResultt.Error -> _uiState.update {
                            it.copy(
                                message = result.message,
                                isLoading = false
                            )
                        }

                        is NetworkResultt.Loading -> _uiState.update { it.copy(isLoading = true) }

                        is NetworkResultt.Success -> _uiState.update {
                            it.copy(
                                changed = true, message = result.message, isLoading = false
                            )
                        }
                    }
                }
        }
    }

    private fun extractErrorMessage(error: String): String {
        val regex = """"message":"([^"]+)"""".toRegex()
        val matchResult = regex.find(error)
        return matchResult?.groupValues?.getOrNull(1) ?: "Error"
    }

}