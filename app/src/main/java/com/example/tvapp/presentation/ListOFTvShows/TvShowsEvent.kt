package com.example.tvapp.presentation.ListOFTvShows

import com.example.tvapp.domain.models.TvShow

sealed class TvShowsEvent {
    data class OnTvShowClick(val tvShow: TvShow): TvShowsEvent()
    object OnAddFavoriteTvShow: TvShowsEvent()
    object OnRefresh: TvShowsEvent()
}
