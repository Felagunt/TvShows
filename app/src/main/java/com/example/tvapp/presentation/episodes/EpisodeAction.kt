package com.example.tvapp.presentation.episodes

import com.example.tvapp.domain.models.Episode


sealed interface EpisodeAction {
    data object OnNavigateBackEpisode: EpisodeAction
    data class OnSelectedEpisodeChange(val episode: Episode): EpisodeAction
}
