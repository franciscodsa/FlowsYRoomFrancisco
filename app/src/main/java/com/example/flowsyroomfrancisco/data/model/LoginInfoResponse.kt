package com.example.flowsyroomfrancisco.data.model

data class LoginInfoResponse(
    val accessToken: String,
    val refreshToken: String,
    val tempPassword: Boolean,
)
