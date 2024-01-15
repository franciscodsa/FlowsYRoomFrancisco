package com.example.flowsyroomfrancisco.ui.list.blogs

import com.example.flowsyroomfrancisco.domain.model.Blog

sealed class BlogsEvent {
    class CreateBlog (val blog: Blog) : BlogsEvent()

    class DeleteBlog (val blogId: Int): BlogsEvent()

    //TODO: RECUERDA AGREGAR EL EVENTO DE MENSAJE VISTO
    object GetAllBlogs: BlogsEvent()
}