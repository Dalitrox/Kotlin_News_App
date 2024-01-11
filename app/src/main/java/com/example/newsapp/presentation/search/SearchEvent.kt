package com.example.newsapp.presentation.search

sealed class SearchEvent {
    data class SearchQueryUpdate(
        val searchQuery: String
    ): SearchEvent()

    object NewsSearch : SearchEvent()
}