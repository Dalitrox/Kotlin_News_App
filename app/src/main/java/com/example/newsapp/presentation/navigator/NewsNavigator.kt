package com.example.newsapp.presentation.navigator

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.R
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.bookmark.BookmarkScreen
import com.example.newsapp.presentation.bookmark.BookmarkViewModel
import com.example.newsapp.presentation.details.DetailsViewModel
import com.example.newsapp.presentation.details.NewsDetailScreen
import com.example.newsapp.presentation.details.NewsDetailsEvent
import com.example.newsapp.presentation.home.HomeScreen
import com.example.newsapp.presentation.home.HomeViewModel
import com.example.newsapp.presentation.navGraph.Route
import com.example.newsapp.presentation.navigator.components.NewsBottomNavigation
import com.example.newsapp.presentation.search.SearchScreen
import com.example.newsapp.presentation.search.SearchViewModel

@Composable
fun NewsNavigator(
    modifier: Modifier = Modifier
) {
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(
                icon = R.drawable.home_icon,
                label = "Home"
            ),
            BottomNavigationItem(
                icon = R.drawable.search_icon,
                label = "Search"
            ),
            BottomNavigationItem(
                icon = R.drawable.bookmark_icon,
                label = "Bookmark"
            ),
        )
    }
    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }

    selectedItem = remember(key1 = backStackState) {
        when (backStackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.SearchScreen.route -> 1
            Route.BookmarkScreen.route -> 2
            else -> 0
        }
    }

    val isBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeScreen.route ||
                backStackState?.destination?.route == Route.SearchScreen.route ||
                backStackState?.destination?.route == Route.BookmarkScreen.route
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        bottomBar = {
            if (isBarVisible) {
                NewsBottomNavigation(
                    items = bottomNavigationItems,
                    isSelected = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> NavigateTo(
                                navController = navController,
                                route = Route.HomeScreen.route
                            )
                            1 -> NavigateTo(
                                navController = navController,
                                route = Route.SearchScreen.route
                            )
                            2 -> NavigateTo(
                                navController = navController,
                                route = Route.BookmarkScreen.route
                            )
                        }
                    }
                )
            }
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier
                .padding(bottom = bottomPadding)
        ) {
            composable(route = Route.HomeScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()
                HomeScreen(
                    articles = articles,
                    navigateToSearch = {
                        NavigateTo(
                            navController = navController,
                            route = Route.SearchScreen.route
                        )
                    },
                    navigateToDetail = { article ->
                        NavigateToDetail(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }
            composable(route = Route.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                val searchState = viewModel.searchState.value
                SearchScreen(
                    state = searchState,
                    event = viewModel::onEvent,
                    navigateToDetail = { article ->
                        NavigateToDetail(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }
            composable(route = Route.PageDetailsScreen.route) {
                val viewModel: DetailsViewModel = hiltViewModel()
                if (viewModel.sideEffect != null) {
                    Toast.makeText(LocalContext.current, viewModel.sideEffect, Toast.LENGTH_SHORT).show()
                    viewModel.onEvent(NewsDetailsEvent.RemovingSideEffect)
                }
                navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")?.let { article ->
                    NewsDetailScreen(
                        article = article,
                        event = viewModel::onEvent,
                        canNavigateUp = {
                            navController.navigateUp()
                        }
                    )
                }
            }
            composable(route = Route.BookmarkScreen.route) {
                val viewModel: BookmarkViewModel = hiltViewModel()
                val bookmarkState = viewModel.bookmarkState.value
                BookmarkScreen(
                    bookmarkState = bookmarkState,
                    navigateToDetail = { article ->
                        NavigateToDetail(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }
        }
    }
}

private fun NavigateTo(
    navController: NavController,
    route: String
) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screen ->
            popUpTo(screen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}

private fun NavigateToDetail(
    navController: NavController,
    article: Article
) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(
        route = Route.PageDetailsScreen.route
    )
}