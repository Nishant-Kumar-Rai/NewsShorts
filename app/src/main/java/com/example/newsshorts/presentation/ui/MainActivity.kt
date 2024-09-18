package com.example.newsshorts.presentation.ui

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
import com.example.newsshorts.presentation.ui.common.TopAppBar
import com.example.newsshorts.presentation.ui.navigation.NewsShortsNavHost
import com.example.newsshorts.presentation.ui.theme.NewsShortsTheme
import com.example.newsshorts.presentation.ui.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsShortsTheme {
                val navController = rememberNavController()
                val viewModel: NewsViewModel = hiltViewModel()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { TopAppBar(" News Shorts", viewModel) }
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



