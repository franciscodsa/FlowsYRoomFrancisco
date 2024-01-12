package com.example.flowsyroomfrancisco.ui.inicio.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowsyroomfrancisco.domain.usecases.LoginUsecase
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
class LoginViewModel @Inject constructor(
    private val loginUsecase: LoginUsecase
) : ViewModel() {

    private val _uiState: MutableStateFlow<LoginState> by lazy {
        MutableStateFlow(LoginState())
    }
    val uiState: StateFlow<LoginState> = _uiState

    private val _uiError = Channel<String>()
    val uiError = _uiError.receiveAsFlow()

    fun handleEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> login(event.email, event.password)
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            loginUsecase.invoke(email, password)
                .catch(action = { cause -> _uiError.send(cause.message ?: "") })
                .collect { result ->
                    when (result) {
                        is NetworkResultt.Error -> _uiState.update {
                            it.copy(
                                message = result.message
                            )
                        }

                        is NetworkResultt.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is NetworkResultt.Success -> _uiState.update {
                            it.copy(
                                logged = true
                            )
                        }
                    }
                }
        }
    }
}
