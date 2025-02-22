package com.example.tvapp.presentation.ListOFTvShows

import com.example.tvapp.data.database.TvShowEntity
import com.example.tvapp.domain.models.TvShow

sealed class TvShowsEvent {
    data class OnTvShowClick(val tvShow: TvShow): TvShowsEvent()
    data class OnSearchQueryChange(val query: String): TvShowsEvent()
    object OnAddFavoriteTvShow: TvShowsEvent()
    object OnRefresh: TvShowsEvent()
    data class OnTabSelected(val index: Int): TvShowsEvent()
}
