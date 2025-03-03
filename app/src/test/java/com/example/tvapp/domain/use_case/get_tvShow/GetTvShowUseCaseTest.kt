package com.example.tvapp.domain.use_case.get_tvShow

import com.example.tvapp.domain.models.TvShowDetail
import com.example.tvapp.domain.repository.ShowsRepository
import com.example.tvapp.util.Resource
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GetTvShowUseCaseTest {

    private val repository: ShowsRepository = mock()

    @Test
    fun test_success() = runTest {
        `when`(repository.getShowById(1)).thenReturn(getShowsDetails())


        val useCase = GetTvShowUseCase(repository)
        val response = useCase.invoke(1)

        assertEquals(getShowsDetails(), response.last().data)
    }

    @Test
    fun test_failing() = runTest {
        `when`(repository.getShowById(1))
            .thenReturn(
               getShowsDetails()
            )
        val useCase = GetTvShowUseCase(repository)
        `when`(useCase.invoke(1)).thenReturn(
            flowOf( Resource.Error("error"))//TODO
        )
        val response = useCase.invoke(1)
       assertEquals("error", response.last().message)
    }
}

//private fun getError(): Resource<TvShowDetail> {
//    return Resource<getShowsDetails()>
//}

private fun getShowsDetails(): TvShowDetail {
    return TvShowDetail(
        id = 1,
        ended = "2015-09-10",
        imdb = "tt1553656",
        thetvdb = 264492,
        tvrage = 25988,
        genres = listOf(
            "Drama",
            "Science-Fiction",
            "Thriller"
        ),
        name = "Under the Dom",
        image = "TODO()",
        originalImg = "TODO()",
        language = "English",
        officialSite = "TODO()",
        premiered = "2013-06-24",
        rating = 6.5,
        runtime = 60,
        status = "Ended",
        summary = "\u003Cp\u003E\u003Cb\u003EUnder the Dome\u003C/b\u003E is the story of a small town that is suddenly and inexplicably sealed off from the rest of the world by an enormous transparent dome. The town's inhabitants must deal with surviving the post-apocalyptic conditions while searching for answers about the dome, where it came from and if and when it will go away.\u003C/p\u003E",
        type = "Scripted",
        url = "TODO()"
    )
}