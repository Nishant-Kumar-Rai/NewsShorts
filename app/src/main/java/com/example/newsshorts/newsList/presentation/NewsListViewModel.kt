package com.example.newsshorts.newsList.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsshorts.core.data.entity.Articles
import com.example.newsshorts.core.domain.NewsRepository
import com.example.newsshorts.core.util.ResultWrapper
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
class NewsListViewModel @Inject constructor(
    private val newsRepo: NewsRepository
) : ViewModel() {

    init {
        getAllNews()
    }

    private val _searchText = MutableStateFlow("");
    val searchText = _searchText.asStateFlow()

    private val _newsListUIState = MutableStateFlow(NewsListUIState())
    val newsListUIState: StateFlow<NewsListUIState> =
        _searchText.combine(_newsListUIState) { queryText, homeState ->
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
            _newsListUIState.value
        )

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    fun onSearchTextChanged(newText: String) {
        _searchText.value = newText
    }

    private fun filterNews(filterKey: String) {
        getAllNews(filterKey)
    }

    fun handleIntents(intents: NewsListActions){
        when(intents){
            is NewsListActions.FilterNews -> {
                filterNews(intents.filterKey)
            }
        }
    }


    private fun getAllNews(filterKey: String = "tesla") {
        viewModelScope.launch {
            newsRepo.getAllNews(filterKey).collect { resourceState ->
                when (resourceState) {
                    is ResultWrapper.Error -> {
                        _newsListUIState.value = _newsListUIState.value.copy(
                            isLoading = false,
                            isError = resourceState.error
                        )
                    }
                    is ResultWrapper.Loading -> {
                        _newsListUIState.value = _newsListUIState.value.copy(
                            isLoading = true
                        )
                    }
                    is ResultWrapper.Success -> {
                        _newsListUIState.value = _newsListUIState.value.copy(
                            isLoading = false,
                            articles = resourceState.data
                        )
                    }
                }
            }
        }
    }

    data class NewsListUIState(
        val isLoading: Boolean = false,
        val isError: String = "",
        val articles: List<Articles> = emptyList(),
        val searchText: String = "",
        val isSearching: Boolean = false
    )

    sealed class NewsListActions{
        data class FilterNews(val filterKey: String): NewsListActions()
    }


}

