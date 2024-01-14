package com.example.flowsyroomfrancisco.ui.list.posts

sealed class PostEvent{

    class GetAllPostsByBlogId(val blogId: Int) : PostEvent()

}
