package com.example.newsshorts.newsList.presentation.components

import android.content.Intent
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.example.newsshorts.R
import com.example.newsshorts.core.data.entity.Articles
import com.example.newsshorts.core.presentation.common.AnnotatedText
import com.example.newsshorts.core.util.Helper
import kotlinx.coroutines.launch

@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    article: Articles,
    onNavigateToDetailsScreen: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    OutlinedCard(
        onClick = { onNavigateToDetailsScreen() },
        modifier = modifier
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
                        Icons.Default.FavoriteBorder,
                        contentDescription = "bookmark"
                    )
                }

                IconButton(onClick = {
                    coroutineScope.launch {
                        val imgBitmap = Helper.loadImageFromUrl(article.urlToImage!!,context)
                        val imageFile = Helper.saveImageToCache(imgBitmap!!,context,article.title)

                        val imgURI = FileProvider.getUriForFile(
                            context, "com.example.newsshorts.provider",
                            imageFile!!
                        )
                        val sendIntent = Intent(Intent.ACTION_SEND).apply {
//                            data = imgURI
                            type = "image/*"
                            putExtra(Intent.EXTRA_STREAM, imgURI)
                            putExtra(Intent.EXTRA_TEXT, article.title)
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        startActivity(context, shareIntent, null)
                    }
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_share_24),
                        contentDescription = "bookmark"
                    )
                }
            }
        }
    }
}