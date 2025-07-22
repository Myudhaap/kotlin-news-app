package dev.mayutama.project.testnewsapp.data.remote.interceptor

import dev.mayutama.project.testnewsapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    private val token = BuildConfig.API_KEY
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", token)
            .addHeader("Accept", "application/json")
            .build()

        return chain.proceed(newRequest)
    }
}