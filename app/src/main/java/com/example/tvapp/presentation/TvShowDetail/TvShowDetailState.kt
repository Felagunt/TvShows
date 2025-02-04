package com.example.tvapp.presentation.TvShowDetail

import com.example.tvapp.domain.models.TvShowDetail

data class TvShowDetailState(
    val isLoading: Boolean = false,
    val tvShow: TvShowDetail? = null,
    val error: String = ""
)