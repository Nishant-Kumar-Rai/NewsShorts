package com.example.newsshorts.core.data.network

import com.example.newsshorts.core.data.entity.NewsApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/everything")
    suspend fun getAllNews(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String = "9d23e15f297442c4bd089dd04cc614bd"
    ): Response<NewsApiResponse>
}