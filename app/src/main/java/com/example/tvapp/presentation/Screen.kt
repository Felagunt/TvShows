package com.example.tvapp.presentation

sealed class Screen(val route: String) {
    object TvShowDetail:Screen("tvshow_detail")
    object ListOfTvShows: Screen("list_of_tvshow")
}
