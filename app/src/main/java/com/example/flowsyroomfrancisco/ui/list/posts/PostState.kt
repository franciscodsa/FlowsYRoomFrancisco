package com.example.flowsyroomfrancisco.ui.list.posts

import com.example.flowsyroomfrancisco.domain.model.Post

data class PostState(
    val posts : List<Post>? = null,
    val isLoading: Boolean = false,
    val message : String? = null
)
