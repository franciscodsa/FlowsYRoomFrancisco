package com.example.flowsyroomfrancisco.ui.list.blogs

import com.example.flowsyroomfrancisco.domain.model.Blog

data class BlogsState(
    val blogs: List<Blog>? = null,
    val isLoading: Boolean = false,
    val message: String? = null
)
