package dev.mayutama.project.testnewsapp.domain.useCase.article

import androidx.paging.PagingData
import androidx.paging.map
import dev.mayutama.project.testnewsapp.domain.model.Article
import dev.mayutama.project.testnewsapp.domain.model.ArticleSource
import dev.mayutama.project.testnewsapp.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllArticleUseCase @Inject constructor(
    private val articleRepository: ArticleRepository
){
    operator fun invoke(source: String, query: String?): Flow<PagingData<Article>> {
        return articleRepository.getAll(source, query)
            .map { pagingData ->
                pagingData.map {
                    Article(
                        ArticleSource(it.source.id, it.source.name),
                        it.author, it.title, it.description, it.url, it.urlToImage, it.publishedAt, it.content)
                }
            }
    }
}