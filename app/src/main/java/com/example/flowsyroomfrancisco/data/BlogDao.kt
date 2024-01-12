package com.example.flowsyroomfrancisco.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flowsyroomfrancisco.data.model.BlogEntity

@Dao
interface BlogDao {

    @Query("SELECT * FROM blogs ORDER BY id DESC")
    suspend fun getAllBlogs(): List<BlogEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertBlog(item: BlogEntity)
}