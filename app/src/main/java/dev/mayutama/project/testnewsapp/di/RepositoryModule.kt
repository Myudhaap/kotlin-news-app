package dev.mayutama.project.testnewsapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mayutama.project.testnewsapp.data.repository.ArticleRepositoryImpl
import dev.mayutama.project.testnewsapp.data.repository.CategoryRepositoryImpl
import dev.mayutama.project.testnewsapp.data.repository.SourceRepositoryImpl
import dev.mayutama.project.testnewsapp.domain.repository.ArticleRepository
import dev.mayutama.project.testnewsapp.domain.repository.CategoryRepository
import dev.mayutama.project.testnewsapp.domain.repository.SourceRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindCategoryRepository(
        impl: CategoryRepositoryImpl
    ): CategoryRepository

    @Binds
    abstract fun bindSourceRepository(
        impl: SourceRepositoryImpl
    ): SourceRepository

    @Binds
    abstract fun bindArticleRepository(
        impl: ArticleRepositoryImpl
    ): ArticleRepository
}