package dev.mayutama.project.testnewsapp.data.remote.interceptor

import dev.mayutama.project.testnewsapp.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

fun setLoggingInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor.Level.BODY
    } else {
        HttpLoggingInterceptor.Level.NONE
    }

    return logging
}