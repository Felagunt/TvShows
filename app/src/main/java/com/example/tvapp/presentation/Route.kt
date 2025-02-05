package com.example.tvapp.presentation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object TvGraph: Route

    @Serializable
    data object TvShowsList: Route

    @Serializable
    data class TvShowDetail(val id: String): Route
}