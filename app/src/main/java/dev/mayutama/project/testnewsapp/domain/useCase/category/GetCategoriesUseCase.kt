package dev.mayutama.project.testnewsapp.domain.useCase.category

import dev.mayutama.project.testnewsapp.data.local.entity.toDomain
import dev.mayutama.project.testnewsapp.domain.model.Category
import dev.mayutama.project.testnewsapp.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke(): Flow<Result<List<Category>>> =
        categoryRepository.getCategories()
            .map {entities ->
                val categories = entities.map { categoryEntity -> categoryEntity.toDomain() }
                Result.success(categories)
            }
            .catch { emit(Result.failure(it)) }
}