package com.example.flowsyroomfrancisco.domain.usecases

import com.example.flowsyroomfrancisco.data.repositories.BlogRepository
import com.example.flowsyroomfrancisco.domain.model.Blog
import com.example.flowsyroomfrancisco.utils.NetworkResultt
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateBlogUsecase @Inject constructor(
    private val blogRepository: BlogRepository
) {
    operator fun invoke(blog: Blog): Flow<NetworkResultt<Blog>> {
        return blogRepository.createBlog(blog)
    }
}