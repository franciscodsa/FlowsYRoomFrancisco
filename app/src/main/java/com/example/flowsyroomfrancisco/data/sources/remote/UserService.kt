package com.example.flowsyroomfrancisco.data.sources.remote

import com.example.flowsyroomfrancisco.data.model.LoginInfoResponse
import com.example.flowsyroomfrancisco.data.model.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("users/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginInfoResponse>
}