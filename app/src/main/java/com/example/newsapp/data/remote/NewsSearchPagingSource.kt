package com.example.newsapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.domain.model.Article

class NewsSearchPagingSource(
    private val api: NewsApi,
    private val searchQuery: String,
    private val sources: String
) : PagingSource<Int, Article>() {
    private var newsCount = 0

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { position ->
            val anchorPage = state.closestPageToPosition(position)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        return try {
            val response = api.newsSearch(
                searchQuery = searchQuery,
                sources = sources,
                page = page
            )
            newsCount += response.articles.size
            val articles = response.articles.distinctBy { it.title } //Remove duplicates
            LoadResult.Page(
                data = articles,
                nextKey = if (newsCount == response.totalResults) null else page + 1,
                prevKey = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(
                throwable = e
            )
        }
    }

}