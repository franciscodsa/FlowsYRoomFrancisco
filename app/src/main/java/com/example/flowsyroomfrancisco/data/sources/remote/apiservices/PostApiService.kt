package com.example.flowsyroomfrancisco.data.sources.remote.apiservices

import com.example.flowsyroomfrancisco.domain.model.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostApiService {
    @GET("posts")
    suspend fun getAllPosts(): Response<List<Post>>

    @GET("posts/{id}")
    suspend fun getPostsByBlogId(@Path("id") id: Int): Response<List<Post>>
}