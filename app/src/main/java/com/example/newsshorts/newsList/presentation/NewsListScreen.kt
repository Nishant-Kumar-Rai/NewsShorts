package com.example.newsshorts.newsList.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsshorts.R
import com.example.newsshorts.core.presentation.common.ErrorScreen
import com.example.newsshorts.core.presentation.common.LoadingScreen
import com.example.newsshorts.newsList.presentation.components.ArticlesScreen
import com.example.newsshorts.newsList.presentation.components.SearchBox

private const val TAG = "HomeScreenTAG"
@Composable
fun NewsListScreen(
    modifier: Modifier? = Modifier,
    isSearchBarVisible: Boolean,
    onNavigateToDetailsScreen: () -> Unit
) {
    val viewModel: NewsListViewModel = hiltViewModel()
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val homeUiState by viewModel.newsListUIState.collectAsStateWithLifecycle()
    var isApple by rememberSaveable {
        mutableStateOf(false)
    }
    var isTesla by rememberSaveable {
        mutableStateOf(true)
    }
    // Error
    if (homeUiState.isError.isNotEmpty()) ErrorScreen(
        errorMessage = homeUiState.isError, R.drawable.baseline_error_24
    )
    // Loading
    if (homeUiState.isLoading)
        LoadingScreen()
    // Success
    Column {
        if (isSearchBarVisible) SearchBox(modifier = Modifier.fillMaxWidth())
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            FilterChip(
                selected = isApple,
                onClick = {
                    isApple = !isApple
                    isTesla = false
                    viewModel.handleIntents(NewsListViewModel.NewsListActions.FilterNews("apple"))
                },
                label = { Text(text = "Apple") },
                modifier = Modifier.padding(8.dp)
            )
            FilterChip(
                selected = isTesla,
                onClick = {
                    isTesla = !isTesla
                    isApple = false
                    viewModel.handleIntents(NewsListViewModel.NewsListActions.FilterNews("tesla"))
                },
                label = { Text(text = "Tesla") },
                modifier = Modifier.padding(8.dp)
            )

        }
        if (homeUiState.articles.isNotEmpty()) {
            ArticlesScreen(
                articles = homeUiState.articles.filter { e ->
                    !e.urlToImage.isNullOrEmpty()
                },
            ) { onNavigateToDetailsScreen() }
        } else {
            if (searchText.isNotEmpty())
                ErrorScreen(
                    errorMessage = "No results found for the search",
                    R.drawable.baseline_search_24
                )
        }
    }


}





