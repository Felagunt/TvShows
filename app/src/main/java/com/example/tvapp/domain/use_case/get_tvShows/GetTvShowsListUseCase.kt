package com.example.tvapp.domain.use_case.get_tvShows

import com.example.tvapp.data.remote.mappers.toTvShow
import com.example.tvapp.domain.models.TvShow
import com.example.tvapp.domain.repository.ShowsRepository
import com.example.tvapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTvShowsListUseCase @Inject constructor(
    private val repository: ShowsRepository
) {

    operator fun invoke(): Flow<Resource<List<TvShow>>> = flow {
        try {
            emit(Resource.Loading())
            val tvShowList = repository.getShows().map { it.toTvShow() }
            emit(Resource.Success(tvShowList))
        } catch (e: HttpException) {
            emit(Resource.Error("Something goes wrong: " + e.localizedMessage))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. " + e.localizedMessage))
        }
    }
}