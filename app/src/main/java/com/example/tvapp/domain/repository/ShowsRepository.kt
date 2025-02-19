package com.example.tvapp.domain.repository

import com.example.tvapp.data.remote.dto.TvShowDto
import com.example.tvapp.data.remote.dto.details.TvShowDetailDto
import com.example.tvapp.domain.models.TvShow
import com.example.tvapp.domain.models.TvShowDetail
import kotlinx.coroutines.flow.Flow

interface ShowsRepository {

    suspend fun getShows(): List<TvShowDto>

    suspend fun getShowById(id: Int): TvShowDetailDto

    fun getFavoriteTvShow(): Flow<List<TvShow>>
    fun isTvShowFavorite(id: Int): Flow<Boolean>
    suspend fun markAsFavorite(tvShow: TvShowDetail)//: Result<Resource.Error>
    suspend fun deleteFromFavorite(id: Int)
}