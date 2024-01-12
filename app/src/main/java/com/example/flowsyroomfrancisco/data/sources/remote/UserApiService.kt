package com.example.flowsyroomfrancisco.data.sources.remote

import com.example.flowsyroomfrancisco.data.model.LoginInfoResponse
import com.example.flowsyroomfrancisco.data.model.LoginRequest
import com.example.flowsyroomfrancisco.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApiService {
    @POST("users/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginInfoResponse>

    @POST("users/register")
    suspend fun register(@Body user: LoginRequest): Response<UserResponse>

    @GET("users/refreshToken")
    suspend fun refreshAccessToken(@Query("refreshToken") refreshToken: String): Response<LoginInfoResponse>
}