package com.example.tvapp.domain.use_case.get_tvShows

import com.example.tvapp.data.remote.dto.Image
import com.example.tvapp.data.remote.dto.TvShowDto
import com.example.tvapp.data.remote.mappers.result
import com.example.tvapp.domain.models.TvShow
import com.example.tvapp.domain.repository.ShowsRepository
import com.example.tvapp.util.Resource
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.junit.Assert.assertEquals

class GetTvShowsListByNameUseCaseTest {

    private val repository: ShowsRepository = mock()
    private val getUseCase: GetTvShowsListByNameUseCase = mock()

    @Test
    fun test_success() = runTest {
        `when`(repository.searchTvShow("bitten"))
            .thenReturn(searchShows())
        val useCase = GetTvShowsListByNameUseCase(repository)
        val response = useCase.invoke("bitten")

        assertEquals(searchShows(), response.last().data)
    }

    @Test
    fun test_failure() = runTest {//TODO
        `when`(repository.searchTvShow("bitten"))
            .thenReturn(searchShows())
        val useCase = GetTvShowsListByNameUseCase(repository)
        `when`(getUseCase.invoke("bitten"))//TODO mock
            .thenReturn(flowOf( Resource.Error("error")))
        val response = useCase.invoke("bitten")
        response.collect{ result: Resource<List<TvShow>> ->
            result.message
        }

        assertEquals("error", response.last().message)
    }

    @Test
    fun test_exception() = runTest {//TODO
        `when`(repository.searchTvShow("bitten"))
            .thenReturn(searchShows())
        val useCase = GetTvShowsListByNameUseCase(repository)
        `when`(useCase.invoke("bitten"))
            .thenThrow(RuntimeException("error"))
        val response = useCase.invoke("bitten")

        assertEquals("error", response.last().message)
    }


    private fun searchShows(): List<TvShow> {
        return listOf(
            TvShow(
                id = 3,
                name = "Bitten",
                language = "English",
                image = "fdd",
                status = "Ended"
            )
        )
    }
}