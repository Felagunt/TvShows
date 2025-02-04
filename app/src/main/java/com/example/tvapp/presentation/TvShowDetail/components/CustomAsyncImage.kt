package com.example.tvapp.presentation.TvShowDetail.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.tvapp.presentation.TvShowDetail.TvShowDetailState
import kotlin.math.min

@Composable
fun CustomAsyncImage(
    state: TvShowDetailState,
    modifier: Modifier = Modifier
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(state.tvShow!!.image)
            .size(Size.ORIGINAL)
            .build()
    )
    val transition by animateFloatAsState(
        targetValue = if (painter.state is AsyncImagePainter.State.Success) 1f else 0f,
        label = "transSuccess"
    )
    if (painter.state is AsyncImagePainter.State.Loading) {
        LoadingAnimation()
    } else {
        Image(
            painter = painter,
            contentDescription = "Image of ${state.tvShow.name}",
            modifier = modifier
                //.saturation(transition)
                .scale(.8f + (.2f * transition))
                //.graphicsLayer { rotationX = (1f - transition) * 5f }
                .alpha(min(1f, transition / .2f))
        )
    }
}