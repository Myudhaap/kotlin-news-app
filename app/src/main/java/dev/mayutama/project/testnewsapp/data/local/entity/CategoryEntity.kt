package dev.mayutama.project.testnewsapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.mayutama.project.testnewsapp.domain.model.Category

@Entity(
    tableName = "mst_category"
)
data class CategoryEntity(
    @PrimaryKey
    val id: Int,
    val code: String,
    val name: String
)

fun CategoryEntity.toDomain() = Category(id, code, name)
fun Category.toEntity() = CategoryEntity(id, code, name)
