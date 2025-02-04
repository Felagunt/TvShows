package com.example.tvapp.domain.repository

import com.example.tvapp.data.remote.dto.TvShowDto
import com.example.tvapp.data.remote.dto.details.TvShowDetailDto

interface ShowsRepository {

    suspend fun getShows(): List<TvShowDto>

    suspend fun getShowById(id: Int): TvShowDetailDto
}