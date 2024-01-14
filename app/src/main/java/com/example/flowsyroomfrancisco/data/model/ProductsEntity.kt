package com.example.flowsyroomfrancisco.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "products",
    foreignKeys = [
        ForeignKey(entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"]
        )]
)
data class ProductsEntity (
    @ColumnInfo(name = "name")
    val name : String,
    @ColumnInfo(name = "categoryId")
    val categoryId: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)