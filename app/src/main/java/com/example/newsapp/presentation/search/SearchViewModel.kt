package com.example.newsapp.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsapp.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {
    private val _searchState = mutableStateOf(SearchState())
    val searchState: State<SearchState> = _searchState

    fun onEvent(
        event: SearchEvent
    ) {
        when (event) {
            is SearchEvent.SearchQueryUpdate -> {
                _searchState.value = searchState.value.copy(
                    searchQuery = event.searchQuery
                )
            }
            is SearchEvent.NewsSearch -> {
                searchNews()
            }
        }
    }

    private fun searchNews() {
        val articles = newsUseCases.newsSearch(
            searchQuery = searchState.value.searchQuery,
            sources = listOf("bbc-news", "abc-news", "associated-press",
                "bild", "bloomberg", "cnn", "der-tagesspiegel", "die-zeit", "ign",
                "national-geographic", "newsweek", "politico", "reuters")
        ).cachedIn(viewModelScope)

        _searchState.value = searchState.value.copy(
            articles = articles
        )
    }
}