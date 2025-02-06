package com.example.tvapp.presentation.TvShowDetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tvapp.presentation.TvShowDetail.TvShowDetailState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ContentScreen(
    state: TvShowDetailState,
    modifier: Modifier = Modifier,
    //paddingValues: PaddingValues
) {
    state.tvShow?.let {tvShow ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding()
        ) {
            //val painter = rememberAsyncImagePainter()
            CustomAsyncImage(state = state)
            Spacer(modifier = Modifier.height(8.dp))
            RatingsRow(state = state)
            Spacer(modifier = Modifier.height(8.dp))
            TvRow(state = state)
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                Arrangement.Center,
                Alignment.CenterHorizontally
            ) {
                Text(text = "Synopsis:", style = MaterialTheme.typography.bodyLarge)
                Text(text = tvShow.summary, style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(text = "Official site: ", style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = tvShow.officialSite,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "url: ", style = MaterialTheme.typography.titleMedium)
                Text(text = tvShow.url, style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Genres: ", style = MaterialTheme.typography.titleMedium)
                tvShow.genres.forEach { genre ->
                    Text(text = genre, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}