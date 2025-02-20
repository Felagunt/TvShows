package com.example.tvapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteTvShowDao {

    //@Upsert
    @Insert
    suspend fun upsert(tvShow: TvShowEntity)

    @Query("SELECT * FROM tvShowsFavorite")
    fun getFavoriteTvShows(): Flow<List<TvShowEntity>>

    @Query("SELECT * FROM tvShowsFavorite WHERE id = :id")
    suspend fun getFavoriteTvShowById(id: Int): TvShowEntity?

    @Query("DELETE FROM tvShowsFavorite WHERE id = :id")
    suspend fun deleteFavoriteTvShowById(id: Int)
}