package com.example.newsapp.presentation.onboarding

import androidx.annotation.DrawableRes
import com.example.newsapp.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val pageImg: Int
)

val pages = listOf(
    Page(
        title = "Home Screen",
        description = "Home Screen shows list of latest articles.",
        pageImg = R.drawable.onboardingimage1
    ),
    Page(
        title = "Search Screen",
        description = "Allows you to search articles for specific keyword.",
        pageImg = R.drawable.onboardingimage2
    ),
    Page(
        title = "Bookmark Screen",
        description = "Allows you to read saved articles and remove them from bookmark.",
        pageImg = R.drawable.onboardingimage3
    )
)
