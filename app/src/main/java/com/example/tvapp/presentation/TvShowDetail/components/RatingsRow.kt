package com.example.tvapp.presentation.TvShowDetail.components

import android.media.Rating
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.tvapp.presentation.TvShowDetail.TvShowDetailState

@Composable
fun RatingsRow(
    state: TvShowDetailState,
    modifier: Modifier = Modifier
) {
    state.tvShow?.let {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            Arrangement.SpaceEvenly,
            Alignment.CenterVertically
        ) {
            Column {
                Text(text = "Rating", style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = state.tvShow.rating.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Column {
                Text(text = "imdb", style = MaterialTheme.typography.bodyLarge)
                Text(text = state.tvShow.imdb, style = MaterialTheme.typography.bodySmall)
            }
            Column {
                Text(text = "imdb", style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = state.tvShow.tvrage.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Column {
                Text(text = "imdb", style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = state.tvShow.thetvdb.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}