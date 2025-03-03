package com.example.tvapp.data.repository

import com.example.tvapp.data.database.FavoriteTvShowDao
import com.example.tvapp.data.database.TvShowEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeTvShowDao(): FavoriteTvShowDao {

    val favorite = mutableListOf<TvShowEntity>()

    override suspend fun upsert(tvShow: TvShowEntity) {
        favorite.add(tvShow)
    }

    override fun getFavoriteTvShows(): Flow<List<TvShowEntity>> {
        return flowOf(favorite)
    }

    override suspend fun getFavoriteTvShowById(id: Int): TvShowEntity? {
        return favorite.firstOrNull { it.id == id }
    }

    override suspend fun deleteFavoriteTvShowById(id: Int) {
        favorite.removeAll { tvShowEntity ->
            tvShowEntity.id == id
        }
    }
}