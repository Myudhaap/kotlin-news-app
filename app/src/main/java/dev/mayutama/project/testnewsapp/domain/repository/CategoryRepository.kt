package dev.mayutama.project.testnewsapp.domain.repository

import dev.mayutama.project.testnewsapp.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories(): Flow<List<CategoryEntity>>
}