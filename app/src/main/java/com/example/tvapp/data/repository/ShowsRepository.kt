package com.example.tvapp.data.repository

import com.example.tvapp.data.remote.TvShowApi
import com.example.tvapp.data.remote.dto.TvShowDto
import com.example.tvapp.data.remote.dto.details.TvShowDetailDto
import com.example.tvapp.domain.repository.ShowsRepository
import javax.inject.Inject

class ShowsRepositoryImp @Inject constructor(
    private val api: TvShowApi
): ShowsRepository {

    override suspend fun getShows(): List<TvShowDto> {
        return api.getShows()
    }

    override suspend fun getShowById(id: Int): TvShowDetailDto {
        return api.getShowById(id)
    }
}