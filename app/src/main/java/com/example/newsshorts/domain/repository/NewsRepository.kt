package com.example.newsshorts.domain.repository

interface NewsRepository {
    suspend fun getNews()
}