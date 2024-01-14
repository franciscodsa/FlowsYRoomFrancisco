package com.example.flowsyroomfrancisco.domain.usecases

import com.example.flowsyroomfrancisco.data.repositories.UserRepository
import com.example.flowsyroomfrancisco.utils.NetworkResultt
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChangePasswordUsecase @Inject constructor(
    private val userRepository: UserRepository
){
    operator fun invoke(email: String, oldPassWord: String, newPassword: String) : Flow<NetworkResultt<Unit>>{
        return userRepository.changePassword(email, oldPassWord, newPassword)
    }
}