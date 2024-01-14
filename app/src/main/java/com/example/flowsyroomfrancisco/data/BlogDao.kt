package com.example.flowsyroomfrancisco.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flowsyroomfrancisco.data.model.BlogEntity
import com.example.flowsyroomfrancisco.data.model.PostEntity

@Dao
interface BlogDao {

    @Query("SELECT * FROM blogs ORDER BY id ASC")
    fun getAllBlogs(): List<BlogEntity>

    @Query("SELECT * FROM posts WHERE blogId = :blogId ORDER BY id ASC")
    suspend fun getAllPostsByBlogId(blogId: Int): List<PostEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertBlog(blog: BlogEntity)

    @Insert
    fun insertAll(blogList: List<BlogEntity>)

    @Delete
    fun deleteBlogList(blogList: List<BlogEntity>)

    @Query("DELETE FROM blogs")
    fun deleteAllBlogs()
}