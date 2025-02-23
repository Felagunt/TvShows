package com.example.tvapp.presentation.TvShowDetail

import com.example.tvapp.domain.models.Episode
import com.example.tvapp.domain.models.TvShowDetail

data class TvShowDetailState(
    val isLoading: Boolean = false,
    val isFavorite: Boolean = false,
    val tvShow: TvShowDetail? = null,
    val episodes: List<Episode>? = null,
    val error: String = ""
)