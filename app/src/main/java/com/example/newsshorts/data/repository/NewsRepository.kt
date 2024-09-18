package com.example.newsshorts.data.repository

import com.example.newsshorts.data.datasource.NewsNetworkDataSource
import com.example.newsshorts.data.entity.Articles
import com.example.newsshorts.util.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsNetworkDataSource: NewsNetworkDataSource
) {
    suspend fun getAllNews(q: String): Flow<ResourceState<List<Articles>>> = flow {
        emit(ResourceState.Loading())
        val response = newsNetworkDataSource.getAllNews(q)
        if (response.isSuccessful && response.body() != null) {
            emit(ResourceState.Success(response.body()!!.articles))
        } else {
            emit(ResourceState.Error(response.message()))
        }
    }.catch { e -> emit(ResourceState.Error(e.localizedMessage ?: "Unexpected error occurred")) }
        .flowOn(Dispatchers.IO)
}




