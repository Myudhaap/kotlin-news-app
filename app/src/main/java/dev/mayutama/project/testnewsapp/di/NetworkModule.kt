package dev.mayutama.project.testnewsapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.mayutama.project.testnewsapp.data.remote.service.SourceService
import dev.mayutama.project.testnewsapp.data.remote.interceptor.AuthInterceptor
import dev.mayutama.project.testnewsapp.data.remote.interceptor.setLoggingInterceptor
import dev.mayutama.project.testnewsapp.data.remote.retrofit.setOkHttpClient
import dev.mayutama.project.testnewsapp.data.remote.retrofit.setRetrofit
import dev.mayutama.project.testnewsapp.data.remote.service.ArticleService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = setLoggingInterceptor()

    @Provides
    fun provideAuthInterceptor(): AuthInterceptor = AuthInterceptor()

    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = setOkHttpClient(authInterceptor, loggingInterceptor)

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        setRetrofit(okHttpClient)

    @Provides
    fun provideSourceService(retrofit: Retrofit): SourceService =
        retrofit.create(SourceService::class.java)

    @Provides
    fun provideArticleService(retrofit: Retrofit): ArticleService =
        retrofit.create(ArticleService::class.java)
}