package com.example.newsshorts.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsshorts.data.entity.Articles
import com.example.newsshorts.data.repository.NewsRepository
import com.example.newsshorts.util.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    init {
        getAllNews()
    }

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isError = MutableStateFlow("")
    val isError = _isError.asStateFlow()

    private val _isSearchBarShown = MutableStateFlow(false)
    val isSearchBarShown = _isSearchBarShown.asStateFlow()

    private val _searchText = MutableStateFlow("");
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _articleList = MutableStateFlow<List<Articles>>(listOf())
    val articleList = searchText.combine(_articleList) { searchText, list ->
        if (searchText.isBlank()) {
            list
        } else {
            list.filter {
                it.doesMatchSearchQuery(searchText)
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _articleList.value
    )

    fun onSearchTextChanged(newText: String) {
        _searchText.value = newText
    }

    fun modifySearchBarVisibility(isVisible: Boolean) {
        _isSearchBarShown.value = isVisible
    }

    fun filterNews(filterKey: String) {
        _isLoading.value = true
        getAllNews(filterKey)
    }


    private fun getAllNews(filterKey: String = "tesla") {
        viewModelScope.launch {
            newsRepository.getAllNews(filterKey).collect { resourceState ->
                when (resourceState) {
                    is ResourceState.Error -> {
                        _isError.value = resourceState.error
                        _isLoading.value = false
                    }
                    is ResourceState.Loading -> {
                        _isLoading.value = true
                    }
                    is ResourceState.Success -> {
                        _isLoading.value = false
                        _articleList.value = resourceState.data
                    }
                }
            }
        }
    }

    data class HomeUiState(
        val isLoading: Boolean? = false,
        val isError: String? = "",
        val articles: List<Articles>? = null
    )
}