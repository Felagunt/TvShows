package com.example.tvapp.data.remote

import com.example.tvapp.data.remote.dto.TvShowDto
import com.example.tvapp.data.remote.dto.details.TvShowDetailDto
import retrofit2.http.GET
import retrofit2.http.Path

interface TvShowApi {

    @GET("/shows")
    suspend fun getShows(): List<TvShowDto>

    @GET("/shows/{id}")
    suspend fun getShowById(@Path("id") id: Int): TvShowDetailDto
}