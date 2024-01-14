package com.example.flowsyroomfrancisco.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flowsyroomfrancisco.data.model.PostEntity

@Dao
interface PostDao {

    @Query("SELECT * FROM posts ORDER BY id ASC")
    suspend fun getAllPosts(): List<PostEntity>


    @Query("SELECT * FROM posts WHERE blogId = :blogId ORDER BY id ASC")
    suspend fun getAllPostsByBlogId(blogId: Int): List<PostEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertPost(post: PostEntity)


    @Insert
    fun insertAll(postList: List<PostEntity>)

    @Delete
    fun deletePostList(postList: List<PostEntity>)

    @Query("DELETE FROM posts")
    fun deleteAllBlogs()
}