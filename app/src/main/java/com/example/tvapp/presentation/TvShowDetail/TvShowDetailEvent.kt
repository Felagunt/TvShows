package com.example.tvapp.presentation.TvShowDetail

sealed class TvShowDetailEvent {
    data object OnNavigationBack: TvShowDetailEvent()
    object OnAddFavoriteTvShow: TvShowDetailEvent()

}
