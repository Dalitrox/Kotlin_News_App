package com.example.newsapp.presentation.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import com.example.newsapp.R
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.Dimens.medium_padding_1
import com.example.newsapp.presentation.common.ArticleList

@Composable
fun BookmarkScreen(
    modifier: Modifier = Modifier,
    bookmarkState: BookmarkState,
    navigateToDetail: (Article) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(
                top = medium_padding_1,
                start = medium_padding_1,
                end = medium_padding_1
            )
    ) {
        Text(
            text = "Bookmark",
            style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = colorResource(id = R.color.text_title)
        )
        Spacer(modifier = Modifier.height(medium_padding_1))
        ArticleList(
            articles = bookmarkState.articles,
            onClick = {
                navigateToDetail(it)
            }
        )
    }
}