package com.example.tvapp.data.repository

import com.example.tvapp.data.database.FavoriteTvShowDao
import com.example.tvapp.data.remote.TvShowApi
import com.example.tvapp.data.remote.dto.TvShowDto
import com.example.tvapp.data.remote.dto.details.TvShowDetailDto
import com.example.tvapp.data.remote.mappers.toTvShow
import com.example.tvapp.data.remote.mappers.toTvShowDetailEntity
import com.example.tvapp.data.remote.mappers.toTvShowEntity
import com.example.tvapp.domain.models.TvShow
import com.example.tvapp.domain.models.TvShowDetail
import com.example.tvapp.domain.repository.ShowsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShowsRepositoryImp @Inject constructor(
    private val api: TvShowApi,
    private val favoriteTvShowDao: FavoriteTvShowDao
) : ShowsRepository {

    override suspend fun getShows(): List<TvShowDto> {
        return api.getShows()
    }

    override suspend fun getShowById(id: Int): TvShowDetailDto {
        return api.getShowById(id)
    }

    override fun getFavoriteTvShow(): Flow<List<TvShow>> {
        return favoriteTvShowDao.getFavoriteTvShows().map { tvShowEntities ->
            tvShowEntities.map { it.toTvShow() }
        }
    }

    override fun isTvShowFavorite(id: Int): Flow<Boolean> {
        return favoriteTvShowDao
            .getFavoriteTvShows()
            .map { tvShowsEntities ->
                tvShowsEntities.any { it.id == id }
            }
    }

    override suspend fun markAsFavorite(tvShow: TvShowDetail)  {
        return favoriteTvShowDao.upsert(tvShow.toTvShowEntity())
//        return try {
//            Resource.Success(Unit)
//            favoriteTvShowDao.upsert(tvShow.toTvShowEntity())
//        } catch (e: SQLException) {
//            Resource.Error(e.localizedMessage)
    //}
    }

    override suspend fun deleteFromFavorite(id: Int) {
        return favoriteTvShowDao.deleteFavoriteTvShowById(id)
    }


}