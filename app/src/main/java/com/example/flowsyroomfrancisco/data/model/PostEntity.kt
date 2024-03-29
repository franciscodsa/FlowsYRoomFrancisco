package com.example.flowsyroomfrancisco.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "posts",
    foreignKeys = [
        ForeignKey(
            entity = BlogEntity::class,
            parentColumns = ["id"],
            childColumns = ["blogId"]
        )],
    indices = [Index("blogId")]  // Add this line to create an index for blogId
)
data class PostEntity(
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "blogId")
    val blogId: Int,
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int,
)