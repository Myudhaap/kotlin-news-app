package dev.mayutama.project.testnewsapp.domain.useCase.article

import javax.inject.Inject


data class ArticleUseCase @Inject constructor(
    val getAllArticleUseCase: GetAllArticleUseCase
)