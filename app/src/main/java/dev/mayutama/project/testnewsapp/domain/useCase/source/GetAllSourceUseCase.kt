package dev.mayutama.project.testnewsapp.domain.useCase.source

import androidx.paging.PagingData
import androidx.paging.map
import dev.mayutama.project.testnewsapp.data.local.entity.toDomain
import dev.mayutama.project.testnewsapp.domain.model.Source
import dev.mayutama.project.testnewsapp.domain.repository.SourceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllSourceUseCase @Inject constructor(
    private val repository: SourceRepository
) {
    operator fun invoke(query: String): Flow<PagingData<Source>> {
        return repository.getSources(query).map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }
}