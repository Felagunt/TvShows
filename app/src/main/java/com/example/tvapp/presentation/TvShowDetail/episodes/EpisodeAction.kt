package com.example.tvapp.presentation.TvShowDetail.episodes


sealed interface EpisodeAction {
    data object OnNavigateBackEpisode: EpisodeAction
}
