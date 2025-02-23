package com.example.tvapp.domain.repository

import com.example.tvapp.domain.models.Episode
import com.example.tvapp.domain.models.TvShow
import com.example.tvapp.domain.models.TvShowDetail
import kotlinx.coroutines.flow.Flow

interface ShowsRepository {

    suspend fun getShows(): List<TvShow>

    suspend fun getShowById(id: Int): TvShowDetail

    fun getFavoriteTvShow(): Flow<List<TvShow>>
    fun isTvShowFavorite(id: Int): Flow<Boolean>
    suspend fun markAsFavorite(tvShow: TvShowDetail)//: Result<Resource.Error>
    suspend fun deleteFromFavorite(id: Int)

    suspend fun searchTvShow(name: String): List<TvShow>

    suspend fun getTvShowsEpisodes(id: Int): List<Episode>
}