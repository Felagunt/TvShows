package com.example.tvapp.presentation.TvShowDetail

import com.example.tvapp.domain.models.TvShow

sealed interface TvShowDetailEvent {
    data object OnNavigationBack: TvShowDetailEvent
    data object OnAddFavoriteTvShow: TvShowDetailEvent

}
