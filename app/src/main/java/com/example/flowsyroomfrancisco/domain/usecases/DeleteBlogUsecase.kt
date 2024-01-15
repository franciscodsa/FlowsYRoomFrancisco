package com.example.flowsyroomfrancisco.domain.usecases

import com.example.flowsyroomfrancisco.data.repositories.BlogRepository
import com.example.flowsyroomfrancisco.utils.NetworkResultt
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteBlogUsecase @Inject constructor(
    private val blogRepository: BlogRepository
) {
    operator fun invoke(blogId: Int): Flow<NetworkResultt<Unit>> {
        return blogRepository.deleteBlog(blogId)
    }
}