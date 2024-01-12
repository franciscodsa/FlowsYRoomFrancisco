package com.example.flowsyroomfrancisco.data.model

import com.example.flowsyroomfrancisco.domain.model.Blog
import com.example.flowsyroomfrancisco.domain.model.Post

fun BlogEntity.toBlog() : Blog{
    return Blog(this.id, this.name, null)
}

fun BlogWithPosts.toBlog() : Blog{
    return Blog(this.blog.id, this.blog.name, this.posts?.map { it.toPost() })
}

fun PostEntity.toPost(): Post{
    return Post(this.title, this.content, this.id)
}

fun Blog.toBlogEntity() : BlogEntity = BlogEntity(this.id, this.name)

fun Blog.toBlogWithPosts(): BlogWithPosts = BlogWithPosts(this.toBlogEntity(), this.posts?.map{
    it.toPostEntity(this.id)
})

fun Post.toPostEntity(blogId: Int = 0): PostEntity = PostEntity(title, content, blogId, id)