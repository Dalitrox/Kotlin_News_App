package com.example.newsapp.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.Dimens.small_padding_2
import com.example.newsapp.presentation.Dimens.medium_padding_1

@Composable
fun ArticleList(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onClick: (Article) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(medium_padding_1),
        contentPadding = PaddingValues(all = small_padding_2)
    ) {
        items(count = articles.size) {
            val article = articles[it]
            ArticleCard(article = article, onClick = { onClick(article) })
        }
    }
}

@Composable
fun ArticleList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onClick: (Article) -> Unit
) {
    val pagingResultHandler = PagingResultHandler(
        articles = articles
    )
    if (pagingResultHandler) {
        LazyColumn(
           modifier = modifier
               .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(medium_padding_1),
            contentPadding = PaddingValues(all = small_padding_2)
        ) {
            items(count = articles.itemCount) {
                articles[it]?.let {
                    ArticleCard(article = it, onClick = { onClick(it) })
                }
            }
        }
    }
}

@Composable
fun PagingResultHandler(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
): Boolean {
    val loadState = articles.loadState
    val checkErrors = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }

    return when {
        loadState.refresh is LoadState.Loading -> {
            ArticleShimmerEffect()
            false
        }
        checkErrors != null -> {
            EmptyScreen()
            false
        }
        else -> {
            true
        }
    }
}

@Composable
private fun ArticleShimmerEffect(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(medium_padding_1)
    ) {
        repeat(10) {
            ArticleCardShimmer(
                modifier = Modifier
                    .padding(horizontal = medium_padding_1)
            )
        }
    }
}