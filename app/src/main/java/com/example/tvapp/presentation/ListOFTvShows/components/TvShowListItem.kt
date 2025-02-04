package com.example.tvapp.presentation.ListOFTvShows.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.tvapp.domain.models.TvShow

@Composable
fun TvShowListItem(
    tvShow: TvShow,
    //OnNavigateToTvShow: (TvShow) -> Unit,
    modifier: Modifier = Modifier
) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = CenterVertically
        ) {
//            GlideImage(
//                imageModel = tvShow.image,
//                modifier = modifier,
//                loading = {
//                    CircularProgressIndicator(
//                        modifier = Modifier
//                            .align(CenterVertically)
//                    )
//                },
//                failure = {
//                    Text(text = "")
//                }
//            )

            val painter = rememberAsyncImagePainter(model = ImageRequest.Builder(LocalContext.current)
                .data(tvShow.image)
                .size(coil.size.Size.ORIGINAL)
                .build()
            )
            //val state = painter.state

//            SubcomposeAsyncImage(
//                model = "https://example.com/image.jpg",
//                contentDescription = "Tv show's ${tvShow.name} image"
//            ) {
//                val state = painter.state
//                if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
//                    CircularProgressIndicator()
//                } else {
//                    SubcomposeAsyncImageContent()
//                }
//            }
//
//            AsyncImage(
//                model = tvShow.image,
//                contentDescription =
//            )
            if(painter.state is AsyncImagePainter.State.Loading ) {
                CircularProgressIndicator()
            } else {
                Image(
                    painter = painter,
                    contentDescription = "Tv show's ${tvShow.name} image",
                    modifier = Modifier
                        .alpha(1f)
                )
            }
            Column(
                modifier = modifier
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = tvShow.name,
                    style = MaterialTheme.typography.headlineLarge,
                    overflow = TextOverflow.Ellipsis
                )
                Divider(modifier = modifier.fillMaxWidth(), color = Color.Gray)
                Text(
                    text = tvShow.status,
                    color =if(tvShow.status == "ended") Color.Gray else Color.Green,
                    style = MaterialTheme.typography.titleMedium
                )
                Divider(modifier = modifier.fillMaxWidth(), color = Color.Gray)
                Text(
                    text = tvShow.language,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

}