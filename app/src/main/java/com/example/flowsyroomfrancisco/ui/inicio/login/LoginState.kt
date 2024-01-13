package com.example.flowsyroomfrancisco.ui.inicio.login

data class LoginState(
    val logged: Boolean = false,
    val isLoading: Boolean = false,
    val isTempPassword: Boolean= false,
    val message: String? = null
)