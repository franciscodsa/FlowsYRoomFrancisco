package com.example.flowsyroomfrancisco.domain.usecases

import com.example.flowsyroomfrancisco.data.model.LoginInfoResponse
import com.example.flowsyroomfrancisco.data.repositories.UserRepository
import com.example.flowsyroomfrancisco.utils.NetworkResultt
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUsecase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<NetworkResultt<LoginInfoResponse>> {
        return userRepository.login(email, password)
    }
}