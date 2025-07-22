package dev.mayutama.project.testnewsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.mayutama.project.testnewsapp.data.pagingSource.ArticlePagingSource
import dev.mayutama.project.testnewsapp.data.remote.dto.res.ItemArticle
import dev.mayutama.project.testnewsapp.data.remote.service.ArticleService
import dev.mayutama.project.testnewsapp.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val articleService: ArticleService
): ArticleRepository {
    override fun getAll(source: String, query: String?): Flow<PagingData<ItemArticle>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ArticlePagingSource(articleService, source, query)
            }
        ).flow
    }
}