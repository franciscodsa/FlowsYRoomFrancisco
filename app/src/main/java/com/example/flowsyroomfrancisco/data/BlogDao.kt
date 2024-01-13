package com.example.flowsyroomfrancisco.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flowsyroomfrancisco.data.model.BlogEntity

@Dao
interface BlogDao {

    @Query("SELECT * FROM blogs ORDER BY id DESC")
    fun getAllBlogs(): List<BlogEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertBlog(blog: BlogEntity)

    @Insert
    fun insertAll(blogList: List<BlogEntity>)

    @Delete
    fun deleteBlogList(blogList: List<BlogEntity>)
}