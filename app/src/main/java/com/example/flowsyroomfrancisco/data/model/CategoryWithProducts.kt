package com.example.flowsyroomfrancisco.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithProducts (
    @Embedded val category : CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val products: List<ProductsEntity>?
)