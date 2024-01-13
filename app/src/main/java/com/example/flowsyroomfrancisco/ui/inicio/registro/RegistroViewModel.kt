package com.example.flowsyroomfrancisco.ui.inicio.registro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flowsyroomfrancisco.domain.usecases.RegisterUsecase
import com.example.flowsyroomfrancisco.ui.inicio.login.LoginEvent
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
class RegistroViewModel @Inject constructor(
    private val registerUsecase: RegisterUsecase
): ViewModel() {

    private val _uiState: MutableStateFlow<RegistroState> by lazy {
        MutableStateFlow(RegistroState())
    }

    val uiState: StateFlow<RegistroState> = _uiState

    private val _uiError = Channel<String>()
    val uiError = _uiError.receiveAsFlow()

    fun handleEvent(event: RegistroEvent) {
        when (event) {
            is RegistroEvent.Register -> register(event.email, event.password)
        }
    }

    private fun register(email: String, password: String) {
        viewModelScope.launch {
            registerUsecase.invoke(email, password)
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
                                result = true
                            )
                        }
                    }
                }
        }
    }

}