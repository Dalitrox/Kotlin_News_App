package com.example.newsapp.presentation.details

import com.example.newsapp.domain.model.Article

sealed class NewsDetailsEvent {
    data class UpsertDeleteArticle(
        val article: Article
    ) : NewsDetailsEvent()

    object RemovingSideEffect : NewsDetailsEvent()
}