package com.example.newsapp.presentation.navGraph

sealed class Route(
    val route: String
) {
    object OnBoardingScreen : Route(route = "onBoardingScreen")
    object HomeScreen : Route(route = "homeScreen")
    object SearchScreen : Route(route = "searchScreen")
    object BookmarkScreen : Route(route = "bookmarkScreen")
    object PageDetailsScreen : Route(route = "pageDetailsScreen")

    object StartNavigation : Route(route = "startNavigation")
    object NewsNavigation : Route(route = "newsNavigation")
    object NewsNavigatorScreen : Route(route = "newsNavigator")
}