package com.example.newsapp.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.R
import com.example.newsapp.presentation.Dimens
import com.example.newsapp.presentation.Dimens.medium_padding_1
import com.example.newsapp.ui.theme.NewsAppTheme

fun Modifier.shimmerEffect() = composed {
    val transition = rememberInfiniteTransition()
    val alpha = transition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000
            ),
            repeatMode = RepeatMode.Reverse
        )
    ).value
    background(
        color = colorResource(id = R.color.shimmer).copy(
            alpha = alpha
        )
    )
}

@Composable
fun ArticleCardShimmer(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        val context = LocalContext.current

        Box(
            modifier = Modifier
                .size(Dimens.card_size)
                .clip(MaterialTheme.shapes.medium)
                .shimmerEffect(),
        )
        Column(
            modifier = Modifier
                .padding(horizontal = Dimens.small_padding_1)
                .height(Dimens.card_size),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Box(
                modifier = Modifier
                    .shimmerEffect()
                    .fillMaxWidth()
                    .height(30.dp)
                    .padding(horizontal = medium_padding_1),
            )
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .shimmerEffect()
                        .fillMaxWidth(0.5f)
                        .height(15.dp)
                        .padding(horizontal = medium_padding_1),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ArticleCardShimmerPreview() {
    NewsAppTheme {
        ArticleCardShimmer()
    }
}