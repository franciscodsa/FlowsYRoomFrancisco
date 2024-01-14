package com.example.flowsyroomfrancisco.domain.usecases


import com.example.flowsyroomfrancisco.data.repositories.PostRepository
import com.example.flowsyroomfrancisco.domain.model.Post
import com.example.flowsyroomfrancisco.utils.NetworkResultt
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostsByBlogIdUsecase @Inject constructor(
    private val postRepository: PostRepository
) {
    operator fun invoke(blogId : Int): Flow<NetworkResultt<List<Post>>>{
        return postRepository.getAllPostsByBlogId(blogId)
    }
}