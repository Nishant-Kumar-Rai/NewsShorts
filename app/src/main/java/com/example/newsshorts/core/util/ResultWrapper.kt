package com.example.newsshorts.core.util

sealed class ResultWrapper<T> {
    class Loading<T> : ResultWrapper<T>()
    data class Success<T>(val data: T) : ResultWrapper<T>()
    data class Error<T>(val error: String) : ResultWrapper<T>()
}