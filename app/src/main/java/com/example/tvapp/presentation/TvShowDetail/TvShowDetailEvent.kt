package com.example.tvapp.presentation.TvShowDetail

import com.example.tvapp.domain.models.Episode

sealed interface TvShowDetailEvent {
    data object OnNavigationBack: TvShowDetailEvent
    data object OnAddFavoriteTvShow: TvShowDetailEvent
    data class  OnEpisodeClick(val episode: Episode): TvShowDetailEvent
}
