package com.example.newsshorts.presentation.ui.navigation

import kotlinx.serialization.Serializable

sealed interface NewsShortsScreens {

    @Serializable
    data object HomeScreenRoute : NewsShortsScreens

    @Serializable
    data object DetailsScreenRoute : NewsShortsScreens

}






