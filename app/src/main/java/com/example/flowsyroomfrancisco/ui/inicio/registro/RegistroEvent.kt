package com.example.flowsyroomfrancisco.ui.inicio.registro


sealed class RegistroEvent {
    class Register (val email: String, val password: String): RegistroEvent()
}