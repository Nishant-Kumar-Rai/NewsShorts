package com.example.newsshorts.core.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface ScreenDestinations {

    @Serializable
    data object NewsListScreen : ScreenDestinations

    @Serializable
    data class NewsDetailsScreen(val newsId: String) : ScreenDestinations

}






