package com.example.flowsyroomfrancisco.data.model

import java.time.LocalDateTime

data class UserResponse(
    val  email : String,

    val  password:String,
    val activated: Boolean,
    val  verificationCode:String,
    val  verificationCodeExpiration: LocalDateTime,
    val  role :String,
    val  loginInfo: LoginInfoResponse,
)
