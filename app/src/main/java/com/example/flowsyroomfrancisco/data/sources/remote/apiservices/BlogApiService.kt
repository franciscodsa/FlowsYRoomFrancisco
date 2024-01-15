package com.example.flowsyroomfrancisco.data.sources.remote.apiservices

import com.example.flowsyroomfrancisco.data.model.BlogEntity
import com.example.flowsyroomfrancisco.domain.model.Blog
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BlogApiService {
    @GET("blogs")
    suspend fun getAllBlogs(): Response<List<Blog>>

    @POST("blogs")
    suspend fun createBlog(@Body blog: BlogEntity): Response<Blog>

    @DELETE("blogs/{id}")
    suspend fun deleteBlog(@Path("id") id: Int): Response<Unit>
}