package com.example.flowsyroomfrancisco.ui.inicio.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowsyroomfrancisco.domain.usecases.ForgotPassworUsecase
import com.example.flowsyroomfrancisco.domain.usecases.LoginUsecase
import com.example.flowsyroomfrancisco.utils.NetworkResultt
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUsecase: LoginUsecase,
    private val forgotPassworUsecase: ForgotPassworUsecase
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
            is LoginEvent.OlvidePassword -> olvidePassword(event.email)
        }
    }

    private fun olvidePassword(email: String) {
        viewModelScope.launch {
            forgotPassworUsecase.invoke(email)
                .catch (action = { cause -> _uiError.send(cause.message ?: "") })
                .collect{result ->
                    when(result){
                        is NetworkResultt.Error -> _uiState.update {
                            it.copy(
                                message = result.message
                            )
                        }
                        is NetworkResultt.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is NetworkResultt.Success -> _uiState.update { it.copy(message = "Email con contraseña enviado") }
                    }
                }
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            loginUsecase.invoke(email, password)
                .catch(action = { cause ->
                    _uiError.send(cause.message ?: "") })
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
                            val tempPassword = result.data?.tempPassword ?: false
                            it.copy(
                                logged = true, isTempPassword = tempPassword, message = result.message, isLoading = false
                            )
                        }
                    }
                }
        }
    }
}
