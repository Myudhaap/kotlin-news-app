package dev.mayutama.project.testnewsapp.data.repository

import dev.mayutama.project.testnewsapp.data.local.dao.CategoryDao
import dev.mayutama.project.testnewsapp.data.local.entity.CategoryEntity
import dev.mayutama.project.testnewsapp.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {
    override fun getCategories():  Flow<List<CategoryEntity>> = categoryDao.getAll();
}