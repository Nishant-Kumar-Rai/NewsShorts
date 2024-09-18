package com.example.newsshorts.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsshorts.presentation.ui.screens.details_screen.DetailsScreen
import com.example.newsshorts.presentation.ui.screens.homescreen.HomeScreen
import com.example.newsshorts.presentation.ui.viewmodel.NewsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun NewsShortsNavHost(
    modifier: Modifier = Modifier,
    startDestination: NewsShortsScreens = NewsShortsScreens.HomeScreenRoute,
    navController: NavHostController,

) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable<NewsShortsScreens.HomeScreenRoute> {
            HomeScreen(true) {}
        }

        composable<NewsShortsScreens.DetailsScreenRoute> { DetailsScreen() }

    }
}