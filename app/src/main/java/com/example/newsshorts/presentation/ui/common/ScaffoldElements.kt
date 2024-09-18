package com.example.newsshorts.presentation.ui.common

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.newsshorts.presentation.ui.viewmodel.NewsViewModel

private const val TAG = "HomeScreenTAG"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String,
    viewModel: NewsViewModel
) {
    Log.d(TAG, "TopAppBar: $viewModel")
    val isSearchBarShown by viewModel.isSearchBarShown.collectAsState()
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = {
                viewModel.modifySearchBarVisibility(!isSearchBarShown)
            }) {
                val icon =
                    if (!isSearchBarShown) Icons.Default.Search else Icons.Default.Close
                Icon(
                    imageVector = icon,
                    contentDescription = "search"
                )
            }
        }
    )
}