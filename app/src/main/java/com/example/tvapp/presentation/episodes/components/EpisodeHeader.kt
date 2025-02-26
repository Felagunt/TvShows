package com.example.tvapp.presentation.episodes.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tvapp.domain.models.Episode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodeHeader(
    episode: Episode,
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Champion's details",
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    onClickBack()
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
    ListItem(
        modifier = modifier,
        headlineContent = {
            Text(
                text = episode.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        supportingContent = {
            Text(
                text = "Season ${episode.season} episode ${episode.number}",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        leadingContent = {
            AsyncImage(
                model = episode.image,
                contentDescription = episode.name,
                modifier = Modifier.size(40.dp)
            )
        },
        trailingContent = {
            Text(
                text = "${episode.runtime}",
                style = MaterialTheme.typography.displayMedium
            )
        }
    )
}