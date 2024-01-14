package com.example.flowsyroomfrancisco.ui.inicio.olvide

sealed class OlvideEvent {

    //TODO: DEBERIA DE CREAR UN OBJETO USER PARA ENVIAR ENTRE CAPAZ QUE TENGA UN EMAIL Y SU CONTRASEÑA Y CONFIRM CONTRASEÑA
    class ChangerPassword(val email: String, val oldPassword: String, val newPassword: String): OlvideEvent()
}