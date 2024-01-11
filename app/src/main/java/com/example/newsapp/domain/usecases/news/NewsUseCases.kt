package com.example.newsapp.domain.usecases.news

data class NewsUseCases(
    val getNews: GetNews,
    val newsSearch: NewsSearch,
    val upsertArticle: UpsertArticle,
    val deleteArticle: DeleteArticle,
    val selectArticles: SelectArticles,
    val selectArticle: SelectArticle
)
