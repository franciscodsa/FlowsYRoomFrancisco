package com.example.flowsyroomfrancisco.data.sources.remote

import com.example.flowsyroomfrancisco.data.model.BlogEntity
import com.example.flowsyroomfrancisco.domain.model.Blog
import retrofit2.Response
import retrofit2.http.GET

interface BlogApiService {
    @GET("blogs")
    suspend fun getAllBlogs(): Response<List<Blog>>
}