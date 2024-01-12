package com.example.flowsyroomfrancisco.domain.model



data class Blog(
    val id: Int,
    val name: String,
    val posts: List<Post>?
)
