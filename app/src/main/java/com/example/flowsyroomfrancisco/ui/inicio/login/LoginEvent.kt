package com.example.flowsyroomfrancisco.ui.inicio.login

sealed class LoginEvent {

    class Login(val email: String, val password: String): LoginEvent()

    class OlvidePassword(val email: String): LoginEvent()
}