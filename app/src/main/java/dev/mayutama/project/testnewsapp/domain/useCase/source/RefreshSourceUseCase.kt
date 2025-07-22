package dev.mayutama.project.testnewsapp.domain.useCase.source

import dev.mayutama.project.testnewsapp.domain.repository.SourceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefreshSourceUseCase @Inject constructor(
    private val repository: SourceRepository
) {
    operator fun invoke(category: String): Flow<Result<Unit>> = flow {
        try {
            repository.fetchFromApi(category)
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}