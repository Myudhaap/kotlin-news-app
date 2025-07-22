package dev.mayutama.project.testnewsapp.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.mayutama.project.testnewsapp.data.remote.dto.res.ItemArticle
import dev.mayutama.project.testnewsapp.data.remote.service.ArticleService

class ArticlePagingSource(
    private val articleService: ArticleService,
    private val source: String,
    private val query: String?
): PagingSource<Int, ItemArticle>() {
    override fun getRefreshKey(state: PagingState<Int, ItemArticle>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemArticle> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize

            val response = articleService.getAll(page = page, pageSize = pageSize, query = query, sources = source)

            LoadResult.Page(
                data = response.articles,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.articles.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}