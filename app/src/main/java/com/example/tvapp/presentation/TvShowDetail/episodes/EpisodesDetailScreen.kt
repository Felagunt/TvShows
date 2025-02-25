package com.example.tvapp.presentation.TvShowDetail.episodes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EpisodesDetailScreenRoot(
    viewModel: EpisodesViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    EpisodesDetailsScreen(
        state = state as EpisodeState,
        onAction = { action ->
            when (action) {
                is EpisodeAction.OnNavigateBackEpisode -> {
                    onBackClick()
                }

                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EpisodesDetailsScreen(
    state: EpisodeState,
    onAction: (EpisodeAction) -> Unit
) {

    TopAppBar(
        modifier = Modifier,
        title = {
            Text(
                text = state.episode?.name ?: ""
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                onAction(EpisodeAction.OnNavigateBackEpisode)
            }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Navigate back"
                )
            }
        }
    )
    Text (text = state.episode.toString())

}