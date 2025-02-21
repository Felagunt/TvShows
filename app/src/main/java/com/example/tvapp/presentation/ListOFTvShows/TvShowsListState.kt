package com.example.tvapp.presentation.ListOFTvShows

import com.example.tvapp.domain.models.TvShow

data class TvShowsListState(
    val isLoading: Boolean = false,
    val tvShows: List<TvShow> = emptyList(),
    val favoriteTvShows: List<TvShow> = emptyList(),
    val error: String = "",
    val selectedIndex: Int = 0
)