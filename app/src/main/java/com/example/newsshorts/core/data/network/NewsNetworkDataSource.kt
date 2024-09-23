package com.example.newsshorts.core.data.network

import com.example.newsshorts.core.data.entity.NewsApiResponse
import com.example.newsshorts.core.domain.NewsDataSource
import retrofit2.Response
import javax.inject.Inject

class NewsNetworkDataSource @Inject constructor(
    private val newsApi: NewsApiService
) : NewsDataSource {
    override suspend fun getAllNews(q: String?): Response<NewsApiResponse> {
        return newsApi.getAllNews(q ?: "")
    }
}