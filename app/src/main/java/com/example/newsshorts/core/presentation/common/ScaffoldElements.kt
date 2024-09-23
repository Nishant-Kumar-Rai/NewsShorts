package com.example.newsshorts.core.presentation.common

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

private const val TAG = "HomeScreenTAG"

// -- App bar should contain how the app bar should look.
// -- Contains UI elements.
// -- Take State as param.
// -- Emit events.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        navigationIcon = navigationIcon,
        actions = actions
    )
}