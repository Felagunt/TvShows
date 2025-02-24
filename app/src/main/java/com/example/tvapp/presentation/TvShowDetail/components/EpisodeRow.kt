package com.example.tvapp.presentation.TvShowDetail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tvapp.domain.models.Episode

@Composable
fun EpisodeRow(
    episodes: List<Episode>,
    onEpisodeClick: (Episode) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = Modifier
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(
            episodes//TODO nice comp list fun, here when circle if empty or list
        ) { episode ->

            EpisodeListItem(
                episode = episode,
                onClick = {
                    onEpisodeClick(episode)
                },
                modifier = Modifier
                    .clickable {
                        onEpisodeClick(episode)
                    }
            )

        }
    }
}