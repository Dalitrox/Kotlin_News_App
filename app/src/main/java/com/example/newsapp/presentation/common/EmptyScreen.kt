package com.example.newsapp.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.example.newsapp.R
import java.net.ConnectException
import java.net.SocketTimeoutException

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
    error: LoadState.Error? = null
) {
    var msg by remember {
        mutableStateOf(parseErrorMessage(error = error))
    }

    var icon by remember {
        mutableStateOf(R.drawable.network)
    }

    if (error == null) {
        msg = "You have not saved any news!"
        icon = R.drawable.search_document
    }

    var animationStart by remember {
        mutableStateOf(false)
    }

    val alphaAnimation by animateFloatAsState(
        targetValue = if (animationStart) 0.3f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )
    
    LaunchedEffect(key1 = true) {
        animationStart = true
    }

    EmptyContent(
        alphaAnimation = alphaAnimation,
        message = msg,
        iconId = icon
    )
}

@Composable
fun EmptyContent(
    alphaAnimation: Float,
    message: String,
    iconId: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
            modifier = Modifier
                .size(120.dp)
                .alpha(alphaAnimation)
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
            modifier = Modifier
                .padding(10.dp)
                .alpha(alphaAnimation)
        )
    }
}

fun parseErrorMessage(
    error: LoadState.Error?
): String {
    return when (error?.error) {
        is SocketTimeoutException -> {
            "Server Unavailable."
        }
        is ConnectException -> {
            "Internet Unavailable."
        }
        else -> {
            "An Unknown error occurred."
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun EmptyScreenPreview() {
    EmptyContent(
        alphaAnimation = 0.3f,
        message = "Internet Unavailable.",
        iconId = R.drawable.network
    )
}