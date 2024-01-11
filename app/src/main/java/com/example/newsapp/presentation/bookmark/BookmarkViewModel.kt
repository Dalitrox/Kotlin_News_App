package com.example.newsapp.presentation.bookmark

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {
    private val _bookmarkState = mutableStateOf(BookmarkState())
    val bookmarkState: State<BookmarkState> = _bookmarkState

    init {
        getArticles()
    }

    private fun getArticles() {
        newsUseCases.selectArticles().onEach {
            _bookmarkState.value = bookmarkState.value.copy(articles = it.reversed())
        }.launchIn(viewModelScope)
    }
}