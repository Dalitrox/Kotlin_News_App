package com.example.newsapp.presentation.navigator.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.R
import com.example.newsapp.presentation.Dimens.small_padding_2
import com.example.newsapp.presentation.Dimens.icon_size
import com.example.newsapp.presentation.navigator.BottomNavigationItem
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun NewsBottomNavigation(
    modifier: Modifier = Modifier,
    items: List<BottomNavigationItem>,
    isSelected: Int,
    onItemClick: (Int) -> Unit
) {
    NavigationBar(
        modifier = modifier
            .fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 10.dp
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == isSelected,
                onClick = { onItemClick(index) },
                icon = {
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(icon_size)
                        )
                        Spacer(modifier = Modifier.height(small_padding_2))
                        Text(
                            text = item.label,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = colorResource(id = R.color.body),
                    unselectedTextColor = colorResource(id = R.color.body),
                    indicatorColor = MaterialTheme.colorScheme.background
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun NewsBottomNavigationPreview() {
    NewsAppTheme {
        NewsBottomNavigation(
            items = listOf(
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

            ),
            isSelected = 0,
            onItemClick = {}
        )
    }
}