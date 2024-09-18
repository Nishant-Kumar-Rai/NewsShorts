package com.example.newsshorts.presentation.ui.screens.homescreen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.newsshorts.R
import com.example.newsshorts.data.entity.Articles
import com.example.newsshorts.presentation.ui.screens.components.AnnotatedText
import kotlinx.coroutines.launch

private const val TAG = "HomeScreenComponents"
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

@Composable
fun ArticleCard(
    article: Articles,
    onNavigateToDetailsScreen: () -> Unit
) {
    OutlinedCard(
        onClick = { onNavigateToDetailsScreen() },
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp)),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
            ) {
                AsyncImage(
                    model = article.urlToImage,
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.baseline_newspaper_24),
                    error = painterResource(id = R.drawable.baseline_error_24)
                )
                Text(
                    text = article.publishedAt,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(8.dp),
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            AnnotatedText(title = "Title", value = article.title)
            AnnotatedText(title = "Author", value = article.author)
            AnnotatedText(title = "Description", value = article.description)

            Row(
                horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_bookmark_border_24),
                        contentDescription = "bookmark"
                    )
                }

                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_share_24),
                        contentDescription = "bookmark"
                    )
                }
            }
        }
    }
}

