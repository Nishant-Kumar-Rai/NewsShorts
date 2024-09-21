package com.example.newsshorts.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.newsshorts.core.presentation.common.TopAppBar
import com.example.newsshorts.core.presentation.navigation.NewsShortsNavHost
import com.example.newsshorts.core.presentation.theme.NewsShortsTheme
import com.example.newsshorts.newsList.presentation.NewsListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsShortsTheme {
                val navController = rememberNavController()
                val viewModel: NewsListViewModel = hiltViewModel()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { TopAppBar(" News Shorts", {}, {}) }
                ) { innerPadding ->
                    NewsShortsNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}



