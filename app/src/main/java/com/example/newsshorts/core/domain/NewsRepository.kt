package com.example.newsshorts.core.domain

import com.example.newsshorts.core.data.entity.Articles
import com.example.newsshorts.core.util.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getAllNews(q: String): Flow<ResultWrapper<List<Articles>>>
}