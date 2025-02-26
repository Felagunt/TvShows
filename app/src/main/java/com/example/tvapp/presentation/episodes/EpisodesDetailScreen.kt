package com.example.tvapp.presentation.episodes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tvapp.presentation.episodes.components.EpisodeHeader

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
    if(state.isLoading) {
        CircularProgressIndicator()
    }
    if (state.errorMsg!!.isNotBlank()) {
        Text(
            text = "Error: ${state.errorMsg}",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.error
        )
    } else {
        state.episode?.let { episode ->
            EpisodeHeader(
                episode = episode,
                modifier = Modifier,
                onClickBack = {
                    onAction(EpisodeAction.OnNavigateBackEpisode)
                }
            )
        }
    }
}