package com.example.newsapp.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.example.newsapp.domain.model.Article
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.R
import com.example.newsapp.presentation.Dimens.MediumPadding1
import com.example.newsapp.presentation.common.ArticleList
import com.example.newsapp.presentation.common.SearchBar
import com.example.newsapp.presentation.navGraph.Route

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    navigateToSearch: () -> Unit,
    navigateToDetail: (Article) -> Unit
) {
    val titles by remember {
        derivedStateOf {
            if (articles.itemCount > 10) {
                articles.itemSnapshotList.items
                    .slice(IntRange(start = 0, endInclusive = 9))
                    .joinToString(separator = "\uD83d\uDFE5") { it.title }
            } else {
                ""
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = MediumPadding1)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.news_logo), //Home Screen
                contentDescription = null,
                modifier = Modifier
                    .width(160.dp)
                    .height(40.dp)
                    .padding(horizontal = MediumPadding1)
            )
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.height(MediumPadding1))
        SearchBar(
            text = "",
            readOnly = true,
            onValueChange = {},
            onClick = {
                navigateToSearch()
            },
            onSearch = {},
            modifier = Modifier
                .padding(horizontal = MediumPadding1)
        )
        Spacer(modifier = Modifier.height(MediumPadding1))
        Text(
            text = titles,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = MediumPadding1)
                .basicMarquee(),
            fontSize = 12.sp,
            color = colorResource(id = R.color.placeholder)
        )
        Spacer(modifier = Modifier.height(MediumPadding1))
        ArticleList(
            articles = articles,
            onClick = {
                navigateToDetail(it)
            },
            modifier = Modifier
                .padding(horizontal = MediumPadding1)
        )
    }
}