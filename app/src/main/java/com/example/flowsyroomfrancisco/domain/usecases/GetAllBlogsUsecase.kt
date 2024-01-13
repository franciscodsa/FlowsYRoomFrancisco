package com.example.flowsyroomfrancisco.domain.usecases

import com.example.flowsyroomfrancisco.data.repositories.BlogRepository
import com.example.flowsyroomfrancisco.domain.model.Blog
import com.example.flowsyroomfrancisco.utils.NetworkResultt
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllBlogsUsecase @Inject constructor(
    private val blogRepository: BlogRepository
){
    operator fun invoke(): Flow<NetworkResultt<List<Blog>>> {
        return blogRepository.getAllBlogs()
    }
}