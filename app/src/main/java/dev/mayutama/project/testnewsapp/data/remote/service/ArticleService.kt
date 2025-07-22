package dev.mayutama.project.testnewsapp.data.remote.service

import dev.mayutama.project.testnewsapp.data.remote.dto.res.ArticleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleService {
    @GET("everything")
    suspend fun getAll(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("sources") sources: String,
        @Query("q") query: String?
    ): ArticleResponse
}