package com.example.newsshorts.core.data.repository

import com.example.newsshorts.core.data.network.NewsNetworkDataSource
import com.example.newsshorts.core.data.entity.Articles
import com.example.newsshorts.core.domain.NewsRepository
import com.example.newsshorts.core.util.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsNetworkDataSource: NewsNetworkDataSource
) : NewsRepository {

    override suspend fun getAllNews(q: String): Flow<ResultWrapper<List<Articles>>> = flow {
        emit(ResultWrapper.Loading())
        val response = newsNetworkDataSource.getAllNews(q)
        if (response.isSuccessful && response.body() != null) {
            emit(ResultWrapper.Success(response.body()!!.articles))
        } else {
            emit(ResultWrapper.Error(response.message()))
        }
    }.catch { e -> emit(ResultWrapper.Error(e.localizedMessage ?: "Unexpected error occurred")) }
        .flowOn(Dispatchers.IO)
}




