package com.example.newsshorts.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsshorts.data.entity.Articles
import com.example.newsshorts.data.repository.NewsRepository
import com.example.newsshorts.util.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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

    private val _searchText = MutableStateFlow("");
    val searchText = _searchText.asStateFlow()

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> =
        _searchText.combine(_homeUiState) { queryText, homeState ->
            if (queryText.isBlank()) {
                homeState
            } else {
                homeState.copy(articles = homeState.articles.filter {
                    it.doesMatchSearchQuery(queryText)
                })
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _homeUiState.value
        )

    private val _isSearchBarShown = MutableStateFlow(false)
    val isSearchBarShown = _isSearchBarShown.asStateFlow()


    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()



    fun onSearchTextChanged(newText: String) {
        _searchText.value = newText
    }

    fun modifySearchBarVisibility(isVisible: Boolean) {
        _isSearchBarShown.value = isVisible
    }

    fun filterNews(filterKey: String) {
        getAllNews(filterKey)
    }

    fun handleIntents(intents: HomeScreenIntents){
        when(intents){
            is HomeScreenIntents.FilterNews -> {
                getAllNews()
            }
        }

    }


    private fun getAllNews(filterKey: String = "tesla") {
        viewModelScope.launch {
            newsRepository.getAllNews(filterKey).collect { resourceState ->
                when (resourceState) {
                    is ResourceState.Error -> {
                        _homeUiState.value = _homeUiState.value.copy(
                            isLoading = false,
                            isError = resourceState.error
                        )
                    }
                    is ResourceState.Loading -> {
                        _homeUiState.value = _homeUiState.value.copy(
                            isLoading = true
                        )
                    }
                    is ResourceState.Success -> {
                        _homeUiState.value = _homeUiState.value.copy(
                            isLoading = false,
                            articles = resourceState.data
                        )
                    }
                }
            }
        }
    }

    data class HomeUiState(
        val isLoading: Boolean = false,
        val isError: String = "",
        val articles: List<Articles> = emptyList(),
        val searchText: String = "",
        val isSearching: Boolean = false
    )

    sealed class HomeScreenIntents{
        data class FilterNews(val filterKey: String): HomeScreenIntents()
    }
}