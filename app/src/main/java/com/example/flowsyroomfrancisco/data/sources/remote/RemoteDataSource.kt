package com.example.flowsyroomfrancisco.data.sources.remote
import com.example.flowsyroomfrancisco.data.sources.remote.ConstantesSources.error
import com.example.flowsyroomfrancisco.data.model.LoginInfoResponse
import com.example.flowsyroomfrancisco.data.model.LoginRequest
import com.example.flowsyroomfrancisco.data.model.UserResponse
import com.example.flowsyroomfrancisco.utils.NetworkResultt
import com.example.flowsyroomfrancisco.utils.TokenManager
import java.lang.Exception
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val userApiService: UserApiService,
    private val tokenManager: TokenManager
){

    suspend fun login(loginRequest: LoginRequest): NetworkResultt<LoginInfoResponse>{
        try {
            val response = userApiService.login(loginRequest)

            return if (!response.isSuccessful) {
                val errorMessage = response.errorBody()?.string() ?: ConstantesSources.unknownError
                error("$error ${response.code()} : $errorMessage")
            } else {
                val body = response.body()

                body?.let {
                    tokenManager.saveAccessToken(it.accessToken)
                    tokenManager.saveRefreshToken(it.accessToken)
                    return NetworkResultt.Success(it)
                }
                error(ConstantesSources.noData)
            }
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    suspend fun register(loginRequest: LoginRequest): NetworkResultt<UserResponse>{
        try {
            val response = userApiService.register(loginRequest)

            return if (!response.isSuccessful) {
                val errorMessage = response.errorBody()?.string() ?: ConstantesSources.unknownError
                error("$error ${response.code()} : $errorMessage")
            } else {
                val body = response.body()

                body?.let {
                    return NetworkResultt.Success(it)
                }
                error(ConstantesSources.noData)
            }
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }
}