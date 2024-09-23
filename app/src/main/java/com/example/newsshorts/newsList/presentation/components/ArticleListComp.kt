package com.example.newsshorts.newsList.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsshorts.core.data.entity.Articles
import kotlinx.coroutines.launch

@Composable
fun ArticlesScreen(
    articles: List<Articles>,
    onNavigateToDetailsScreen: () -> Unit
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
            state = listState
        ) {
            items(items = articles, key = { item -> item.title }) { article ->
                ArticleCard(article = article) {
                    onNavigateToDetailsScreen()
                }
            }
        }

        FloatingActionButton(onClick = {
            coroutineScope.launch {
                listState.animateScrollToItem(index = 0)
            }
        }) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "",
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}