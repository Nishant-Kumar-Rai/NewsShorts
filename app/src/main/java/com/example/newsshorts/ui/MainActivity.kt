package com.example.newsshorts.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsshorts.R
import com.example.newsshorts.ui.screens.homescreen.HomeScreen
import com.example.newsshorts.ui.theme.NewsShortsTheme
import com.example.newsshorts.ui.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsShortsTheme {
                val viewModel = viewModel<NewsViewModel>()
                val isSearchBarShown by viewModel.isSearchBarShown.collectAsState()

                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text(text = "News Shorts") },
                        navigationIcon = {
                            IconButton(onClick = {
                                viewModel.modifySearchBarVisibility(!isSearchBarShown)
                            }) {
                                val id =
                                    if (!isSearchBarShown) R.drawable.baseline_search_24 else R.drawable.baseline_close_24
                                Icon(
                                    painter = painterResource(id = id),
                                    contentDescription = "search"
                                )
                            }
                        }
                    )
                }, bottomBar = {
                    NavigationBar {

                    }

                }) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NewsAppNavigation()
                    }
                }
            }
        }
    }


}

@Composable
fun NewsAppNavigation(
    viewModel: NewsViewModel = viewModel()
) {
    val isSearchBarVisible by viewModel.isSearchBarShown.collectAsState()
    HomeScreen(isSearchBarVisible = isSearchBarVisible)
}
