package com.example.tvapp.presentation.TvShowDetail.episodes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EpisodesDetailScreenRoot(
    viewModel: EpisodesViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    EpisodesDetailsScreen(
        state = state,
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

@Composable
fun EpisodesDetailsScreen(
    state: EpisodeState,
    onAction: (EpisodeAction) -> Unit
) {

}