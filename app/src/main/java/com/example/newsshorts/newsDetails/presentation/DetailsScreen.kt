package com.example.newsshorts.newsDetails.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {

    }

}

@Preview(
    "Night Mode",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_UNDEFINED,
    showBackground = true
)
@Composable
fun DetailsScreenPreview() {
    DetailsScreen()

}