package com.example.flowsyroomfrancisco.data.sources.remote.apiservices

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

    @POST("users/forgotPassword")
    suspend fun forgotPassword(@Query("email") email: String): Response<Unit>

    @GET("users/refreshToken")
    suspend fun refreshAccessToken(@Query("refreshToken") refreshToken: String): Response<LoginInfoResponse>

    @POST("users/changePassword")
    suspend fun changePassword(
        @Query("email") emai: String,
        @Query("oldPassword") oldPassword: String,
        @Query("newPassword") newPassword: String
    ): Response<Unit>
}