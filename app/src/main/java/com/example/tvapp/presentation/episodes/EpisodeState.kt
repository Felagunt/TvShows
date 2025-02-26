package com.example.tvapp.presentation.episodes

import com.example.tvapp.domain.models.Episode

data class EpisodeState(
    val isLoading: Boolean = false,
    val episode: Episode? = null,
    val errorMsg: String? = null
)
