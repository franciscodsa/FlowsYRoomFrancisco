package com.example.flowsyroomfrancisco.utils

import com.example.flowsyroomfrancisco.data.model.LoginInfoResponse
import com.example.flowsyroomfrancisco.data.sources.remote.ConstantesSources
import com.example.flowsyroomfrancisco.data.sources.remote.apiservices.UserApiService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val tokenManager: TokenManager
): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = runBlocking {
            tokenManager.getRefreshToken().first()
        }
        return runBlocking {
            val newToken = getNewToken(refreshToken)

            if (!newToken.isSuccessful || newToken.body() == null) { //Couldn't refresh the token, so restart the login process
                /*tokenManager.deleteAccessToken()
                tokenManager.deleteRefreshToken()*/
            }

            newToken.body()?.let {
                tokenManager.saveAccessToken(it.accessToken)
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${it}")
                    .build()
            }
        }
    }


    private suspend fun getNewToken(refreshToken: String?): retrofit2.Response<LoginInfoResponse> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(ConstantesSources.urlsBase)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val service = retrofit.create(UserApiService::class.java)

        return service.refreshAccessToken("Bearer $refreshToken")
    }
}