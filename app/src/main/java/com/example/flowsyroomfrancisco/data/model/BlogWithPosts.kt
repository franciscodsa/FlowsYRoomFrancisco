package com.example.flowsyroomfrancisco.data.model
import androidx.room.Embedded
import androidx.room.Relation

data class BlogWithPosts(
    @Embedded val blog: BlogEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "blogId"
    )
    val posts: List<PostEntity>?
)