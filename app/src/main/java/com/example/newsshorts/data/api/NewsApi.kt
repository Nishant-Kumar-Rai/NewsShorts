package com.example.newsshorts.data.api

import com.example.newsshorts.data.entity.NewsApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/everything")
    suspend fun getAllNews(
        @Query("q") q: String,
        @Query("apiKey") apiKey: String = "9d23e15f297442c4bd089dd04cc614bd"
    ): Response<NewsApiResponse>


}