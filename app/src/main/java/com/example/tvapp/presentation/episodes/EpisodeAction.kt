package com.example.tvapp.presentation.episodes

import com.example.tvapp.domain.models.Episode


sealed interface EpisodeAction {
    data class FetchEpisode(val showId: Int, val episodeId: Int): EpisodeAction
    data object OnNavigateBackEpisode: EpisodeAction
    data class OnSelectedEpisodeChange(val episode: Episode): EpisodeAction
}
