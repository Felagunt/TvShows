package com.example.tvapp.domain.use_case.get_tvShows

import com.example.tvapp.domain.models.TvShow
import com.example.tvapp.domain.repository.ShowsRepository
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GetTvShowsListUseCaseTest {//TODO its search by name

    private val repository: ShowsRepository = mock()

    @Test
    fun test_success() = runTest {
        `when`(repository.searchTvShow("bitten"))
            .thenReturn(searchShows())
        val useCase = GetTvShowsListByNameUseCase(repository)
        val response = useCase.invoke("bitten")

        assertEquals(searchShows(), response.last().data)
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