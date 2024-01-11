package com.example.flowsyroomfrancisco.data.model

import java.time.LocalDateTime

data class UserResponse(
    val  email : String,

    val  password:String,
    val activated: Boolean,
    val  verificationCode:String,
    val  verificationCodeExpiration: LocalDateTime,
    val  role :String,
//TODO: PODRIA SER UTIL COLOCAR DENTRO DE USERTOKENS Y CAMBIAR EL NOMBRE A LA CLASE PARA ASI EN LA LLAMADA DEL LOGIN DEVOLVER MAS INFORMACION UTIL COMO SI LA CLAVE ES TEMPORAL Y LA FECHA DEL ULTIMO LOGIN (O LOGOUT) NECESARIA PARA EL 2FA
    val  loginInfo: LoginInfoResponse,
)
