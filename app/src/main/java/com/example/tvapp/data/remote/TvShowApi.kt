package com.example.tvapp.data.remote

import com.example.tvapp.data.remote.dto.TvShowDto
import com.example.tvapp.data.remote.dto.details.TvShowDetailDto
import com.example.tvapp.data.remote.dto.episodes.EpisodesDtoItem
import retrofit2.http.GET
import retrofit2.http.Path

interface TvShowApi {

    @GET("/shows")
    suspend fun getShows(): List<TvShowDto>

    @GET("/shows/{id}")
    suspend fun getShowById(@Path("id") id: Int): TvShowDetailDto

    @GET("/search/shows?q={query}")
    suspend fun searchShows(@Path("query")query: String): List<TvShowDto>

    //https://api.tvmaze.com/shows/1/episodes list of episodes of show with id
    @GET("shows/{id}/episodes")
    suspend fun getEpisodes(@Path("id")id: Int): List<EpisodesDtoItem>
}