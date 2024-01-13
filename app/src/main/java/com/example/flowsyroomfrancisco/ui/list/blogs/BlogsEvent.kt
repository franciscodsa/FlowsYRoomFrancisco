package com.example.flowsyroomfrancisco.ui.list.blogs

sealed class BlogsEvent {
    //TODO: RECUERDA AGREGAR EL EVENTO DE MENSAJE VISTO
    object GetAllBlogs: BlogsEvent()
}