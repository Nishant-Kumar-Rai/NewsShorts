package com.example.newsshorts.ui.screens.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsshorts.ui.viewmodel.NewsViewModel

@Composable
fun SearchBox(
    modifier: Modifier,
    viewModel: NewsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val searchTextValue by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    OutlinedTextField(
        value = searchTextValue,
        onValueChange = viewModel::onSearchTextChanged,
        modifier = modifier.padding(8.dp),
        label = { Text(text = "Type to search news")},
        placeholder = {Text("Search news")}

    )
}