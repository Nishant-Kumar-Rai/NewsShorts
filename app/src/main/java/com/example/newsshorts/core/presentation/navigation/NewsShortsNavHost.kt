package com.example.newsshorts.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsshorts.newsDetails.presentation.DetailsScreen
import com.example.newsshorts.newsList.presentation.NewsListScreen

@Composable
fun NewsShortsNavHost(
    modifier: Modifier = Modifier,
    startDestination: ScreenDestinations = ScreenDestinations.NewsListScreen,
    navController: NavHostController,

    ) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable<ScreenDestinations.NewsListScreen> {
            NewsListScreen(modifier,true) {}
        }

        composable<ScreenDestinations.NewsDetailsScreen> { DetailsScreen() }

    }
}