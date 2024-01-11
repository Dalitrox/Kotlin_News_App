package com.example.newsapp.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.Dimens.medium_padding_1
import com.example.newsapp.presentation.common.ArticleList
import com.example.newsapp.presentation.common.SearchBar

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    state: SearchState,
    event: (SearchEvent) -> Unit,
    navigateToDetail: (Article) -> Unit
) {
    Column(
        modifier = modifier
            .padding(
                top = medium_padding_1,
                start = medium_padding_1,
                end = medium_padding_1
            )
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        SearchBar(
            text = state.searchQuery,
            readOnly = false,
            onValueChange = {
                event(SearchEvent.SearchQueryUpdate(it))
            },
            onSearch = {
                event(SearchEvent.NewsSearch)
            }
        )
        Spacer(modifier = Modifier.height(medium_padding_1))
        state.articles?.let {
            val articles = it.collectAsLazyPagingItems()
            ArticleList(
                articles = articles,
                onClick = {
                    navigateToDetail(it)
                }
            )
        }
    }
}