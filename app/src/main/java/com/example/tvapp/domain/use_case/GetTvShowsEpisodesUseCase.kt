package com.example.tvapp.domain.use_case

import com.example.tvapp.data.remote.mappers.toTvShow
import com.example.tvapp.data.remote.mappers.toTvShowDetail
import com.example.tvapp.domain.models.Episode
import com.example.tvapp.domain.models.TvShow
import com.example.tvapp.domain.models.TvShowDetail
import com.example.tvapp.domain.repository.ShowsRepository
import com.example.tvapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTvShowsEpisodesUseCase @Inject constructor(
    private val repository: ShowsRepository
) {

    operator fun invoke(id: Int): Flow<Resource<List<Episode>>> = flow {
        try {
            emit(Resource.Loading())
            val tvShowsEpisodesList = repository.getTvShowsEpisodes(id)
            emit(Resource.Success(tvShowsEpisodesList))
        } catch (e: HttpException) {
            emit(Resource.Error("Something goes wrong: " + e.localizedMessage))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. " + e.localizedMessage))
        }
    }
}