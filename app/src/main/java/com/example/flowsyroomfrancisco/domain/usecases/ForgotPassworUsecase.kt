package com.example.flowsyroomfrancisco.domain.usecases

import android.provider.ContactsContract.CommonDataKinds.Email
import com.example.flowsyroomfrancisco.data.repositories.UserRepository
import com.example.flowsyroomfrancisco.utils.NetworkResultt
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ForgotPassworUsecase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(email: String): Flow<NetworkResultt<Unit>>{
        return userRepository.forgotPassword(email)
    }

}