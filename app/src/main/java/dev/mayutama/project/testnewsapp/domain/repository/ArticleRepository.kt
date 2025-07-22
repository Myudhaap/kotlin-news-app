package dev.mayutama.project.testnewsapp.domain.repository

import androidx.paging.PagingData
import dev.mayutama.project.testnewsapp.data.remote.dto.res.ItemArticle
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    fun getAll(
        source: String,
        query: String?
    ): Flow<PagingData<ItemArticle>>
}