package com.example.flowsyroomfrancisco.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "blogs")
data class BlogEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String
)
