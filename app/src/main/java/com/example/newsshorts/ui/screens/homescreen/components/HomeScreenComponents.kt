package com.example.newsshorts.ui.screens.homescreen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.newsshorts.R
import com.example.newsshorts.data.entity.Articles
import com.example.newsshorts.ui.screens.components.AnnotatedText

@Composable
fun ArticlesScreen(articles: List<Articles>) {
    LazyColumn {
        items(articles) { article ->
            ArticleCard(article = article)
        }
    }
}

@Composable
fun ArticleCard(article: Articles) {
    OutlinedCard(
        onClick = {}, modifier = Modifier.padding(8.dp), border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
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

