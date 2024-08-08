package com.example.newsshorts.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsApiResponse(
    val status: String,
    val totalResults: String,
    val articles: List<Articles>
) : Parcelable

@Parcelize
data class Articles(
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
) : Parcelable {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            title?:"", author?:""
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}


@Parcelize
data class Source(
    val id: String,
    val name: String
) : Parcelable
