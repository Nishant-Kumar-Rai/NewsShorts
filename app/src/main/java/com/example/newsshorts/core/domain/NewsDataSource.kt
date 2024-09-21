package com.example.newsshorts.core.domain

import com.example.newsshorts.core.data.entity.NewsApiResponse
import retrofit2.Response


interface NewsDataSource {
    suspend fun getAllNews(q: String?): Response<NewsApiResponse>
}