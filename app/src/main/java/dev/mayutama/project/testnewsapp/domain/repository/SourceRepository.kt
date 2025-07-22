package dev.mayutama.project.testnewsapp.domain.repository

import androidx.paging.PagingData
import dev.mayutama.project.testnewsapp.data.local.entity.SourceEntity
import kotlinx.coroutines.flow.Flow

interface SourceRepository {
    fun getSources(q: String): Flow<PagingData<SourceEntity>>
    suspend fun fetchFromApi(category: String)
}