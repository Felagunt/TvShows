package com.example.tvapp.domain.use_case.get_tvShow

import com.example.tvapp.domain.models.TvShowDetail
import com.example.tvapp.domain.repository.ShowsRepository
import com.example.tvapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTvShowUseCase @Inject constructor(
    private val repository: ShowsRepository
) {

    operator fun invoke(id: Int): Flow<Resource<TvShowDetail>> = flow {
        try {
            emit(Resource.Loading())
            val tvShowList = repository.getShowById(id)
            emit(Resource.Success(tvShowList))
        } catch (e: HttpException) {
            emit(Resource.Error("Something goes wrong: " + e.localizedMessage))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. " + e.localizedMessage))
        }
    }
}