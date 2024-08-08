package com.example.newsshorts.data.datasource

import com.example.newsshorts.data.api.NewsApi
import com.example.newsshorts.data.entity.NewsApiResponse
import retrofit2.Response
import javax.inject.Inject

class NewsNetworkDataSource @Inject constructor(
    private val newsApi: NewsApi
) : NewsDataSource {
    override suspend fun getAllNews(q: String?): Response<NewsApiResponse> {
        return newsApi.getAllNews(q ?: "")
    }
}