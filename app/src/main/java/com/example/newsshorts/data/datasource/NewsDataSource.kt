package com.example.newsshorts.data.datasource

import com.example.newsshorts.data.entity.NewsApiResponse
import retrofit2.Response


interface NewsDataSource {
    suspend fun getAllNews(q: String?): Response<NewsApiResponse>
}