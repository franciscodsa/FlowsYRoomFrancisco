package com.example.flowsyroomfrancisco.data.repositories

import com.example.flowsyroomfrancisco.data.model.LoginInfoResponse
import com.example.flowsyroomfrancisco.data.model.LoginRequest
import com.example.flowsyroomfrancisco.data.model.UserResponse
import com.example.flowsyroomfrancisco.data.sources.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.flowsyroomfrancisco.utils.NetworkResultt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class UserRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
){
    fun login(email: String, password: String): Flow<NetworkResultt<LoginInfoResponse>> {
        return flow {
            emit(NetworkResultt.Loading())
            val result = remoteDataSource.login(LoginRequest(email, password))
            emit(result)
            if (result is NetworkResultt.Success) {
                result.data?.let { loginResult ->
                    // Aquí puedes almacenar información del usuario en tu base de datos local si es necesario
                    // userDao.insertUser(loginResult.toUserEntity())
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    fun register(email: String, password: String): Flow<NetworkResultt<UserResponse>> {
        return flow {
            emit(NetworkResultt.Loading())
            val result = remoteDataSource.register(LoginRequest(email, password))
            emit(result)
            if (result is NetworkResultt.Success) {
                result.data?.let { registerResult ->
                    // Aquí puedes almacenar información del usuario en tu base de datos local si es necesario
                    // userDao.insertUser(loginResult.toUserEntity())
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    fun forgotPassword(email: String): Flow<NetworkResultt<Unit>>{
        return flow {
            emit(NetworkResultt.Loading())
            val result=remoteDataSource.forgotPassword(email)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun changePassword(email: String, oldPassword: String, newPassword: String): Flow<NetworkResultt<Unit>>{
        return flow {
            emit(NetworkResultt.Loading())
            val result= remoteDataSource.changePassword(email, oldPassword, newPassword)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}