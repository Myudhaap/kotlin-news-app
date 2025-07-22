package dev.mayutama.project.testnewsapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.mayutama.project.testnewsapp.domain.model.Source

@Entity(
    tableName = "mst_source"
)
data class SourceEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val url: String,
    val category: String,
    val language: String,
    val country: String
)

fun SourceEntity.toDomain() = Source(id, name, description, url, category, language, country)
fun Source.toEntity() = SourceEntity(id, name, description, url, category, language, country)