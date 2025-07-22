package dev.mayutama.project.testnewsapp.data.remote.service

import dev.mayutama.project.testnewsapp.data.remote.dto.res.SourceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SourceService {
    @GET("top-headlines/sources")
    suspend fun getAllSources(
        @Query("category") category: String
    ): SourceResponse
}