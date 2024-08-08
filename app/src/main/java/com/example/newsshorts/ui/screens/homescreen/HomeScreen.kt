package com.example.newsshorts.ui.screens.homescreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsshorts.R
import com.example.newsshorts.ui.screens.components.ErrorScreen
import com.example.newsshorts.ui.screens.components.LoadingScreen
import com.example.newsshorts.ui.screens.components.SearchBox
import com.example.newsshorts.ui.screens.homescreen.components.ArticlesScreen
import com.example.newsshorts.ui.viewmodel.NewsViewModel

@Composable
fun HomeScreen(
    viewModel: NewsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    isSearchBarVisible: Boolean
) {

    val isLoading by viewModel.isLoading.collectAsState()
    val isError by viewModel.isError.collectAsState()
    val articles by viewModel.articleList.collectAsState()
    val searchText by viewModel.searchText.collectAsState()

    var isApple by rememberSaveable {
        mutableStateOf(false)
    }

    var isTesla by rememberSaveable {
        mutableStateOf(true)
    }


    if (isError.isNotEmpty())
        ErrorScreen(errorMessage = isError, R.drawable.baseline_error_24)


    if (isLoading)
        LoadingScreen()



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
                    viewModel.filterNews("apple")
                },
                label = { Text(text = "Apple") },
                modifier = Modifier.padding(8.dp)
            )
            FilterChip(
                selected = isTesla,
                onClick = {
                    isTesla = !isTesla
                    isApple = false
                    viewModel.filterNews("tesla")
                },
                label = { Text(text = "Tesla") },
                modifier = Modifier.padding(8.dp)
            )

        }
        if (articles.isNotEmpty()) {
            ArticlesScreen(articles = articles.filter { e -> e.urlToImage != null && e.urlToImage.isNotEmpty() })
        } else {
            if (searchText.isNotEmpty())
                ErrorScreen(
                    errorMessage = "No results found for the search",
                    R.drawable.baseline_search_24
                )
        }
    }


}





