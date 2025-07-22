package dev.mayutama.project.testnewsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.mayutama.project.testnewsapp.data.local.dao.SourceDao
import dev.mayutama.project.testnewsapp.data.local.entity.SourceEntity
import dev.mayutama.project.testnewsapp.data.remote.service.SourceService
import dev.mayutama.project.testnewsapp.domain.repository.SourceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SourceRepositoryImpl @Inject constructor(
    private val sourceService: SourceService,
    private val sourceDao: SourceDao
): SourceRepository{
    override fun getSources(q: String): Flow<PagingData<SourceEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {sourceDao.getAll(query = q)},
        ).flow
    }

    override suspend fun fetchFromApi(category: String) {
        val response = sourceService.getAllSources(category)
        sourceDao.clearAll()
        sourceDao.insertAll(response.sources.map { SourceEntity(it.id, it.name, it.description, it.url, it.category, it.language, it.country) })
    }
}