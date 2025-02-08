package com.example.tvapp.data.database

import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface FavoriteTvShowDao {

    //@Upsert
    @Insert
    suspend fun upsert(tvShow: TvShowEntity)

    @Query("SELECT * FROM TvShowEntity")
    fun getFavoriteTvShows(): Flow<List<TvShowEntity>>

    @Query("SELECT * FROM TvShowEntity WHERE id = :id")
    suspend fun getFavoriteTvShowById(id: Int): TvShowEntity?

    @Query("DELETE FROM TvShowEntity WHERE id = :id")
    suspend fun deleteFavoriteTvShowById(id: Int)
}