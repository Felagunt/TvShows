package com.example.tvapp.data.repository

import com.example.tvapp.data.database.FavoriteTvShowDao
import com.example.tvapp.data.database.TvShowEntity
import com.example.tvapp.data.remote.TvShowApi
import com.example.tvapp.data.remote.dto.Image
import com.example.tvapp.data.remote.dto.TvShowDto
import com.example.tvapp.data.remote.dto.details.Country
import com.example.tvapp.data.remote.dto.details.Externals
import com.example.tvapp.data.remote.dto.details.Network
import com.example.tvapp.data.remote.dto.details.Previousepisode
import com.example.tvapp.data.remote.dto.details.Schedule
import com.example.tvapp.data.remote.dto.details.TvShowDetailDto
import com.example.tvapp.data.remote.dto.episodes.EpisodesDtoItem
import com.example.tvapp.data.remote.dto.episodes.Links
import com.example.tvapp.data.remote.dto.episodes.Rating
import com.example.tvapp.data.remote.dto.episodes.Self
import com.example.tvapp.data.remote.dto.episodes.Show
import com.example.tvapp.data.remote.mappers.toTvShow
import com.example.tvapp.data.remote.mappers.toTvShowDetail
import com.example.tvapp.domain.models.TvShow
import com.example.tvapp.domain.models.TvShowDetail
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class ShowsRepositoryImpTest {


    private val api: TvShowApi = mock()
    private val favoriteDao: FavoriteTvShowDao = mock()

    @Test
    fun test_search() = runTest {

        val repo = ShowsRepositoryImp(
            api = api,
            favoriteTvShowDao = favoriteDao
        )

        `when`(api.searchShows("Bitten"))
            .thenReturn(searchShows())

        val response = repo.searchTvShow("Bitten")

        assertEquals(searchShows().map { it.toTvShow() }, response)

    }

    @Test
    fun `can search tvShows`() = runTest {
        val response = api.searchShows("bitten")
        assertEquals(response, 200)//TODO
    }

    @Test
    fun `can get tvShows`() = runTest {
        val tvShows = listOf(
            TvShowDto(
                id = 1,
                name = "Under the Dome",
                language = "English",
                image = Image(
                    medium = "",
                    original = ""
                ),
                status = "Ended"
            )
        )
        `when`(api.getShows()).thenReturn(tvShows)
        val response = api.getShows()
        assertEquals(response, tvShows)
    }

    @Test
    fun `can reach Episodes`() = runTest {

        `when`(api.getEpisodes(id = 3)).thenReturn(episodes())
        val response = api.getEpisodes(searchShows().map {
            it.id
        }
            .first())

        assertEquals(response, episodes())
    }

    @Test
    fun test_getFavorite() = runTest {//TODO
        val repo = ShowsRepositoryImp(
            api = api,
            favoriteTvShowDao = favoriteDao
        )
        val favorite = listOf(//TODO wtf here
            TvShowEntity(
                id = 3,
                name = "Bitten",
                language = "English",
                image = "TODO()",
                status = "Ended"
            )
        ).asFlow()//flowOf(listOf(favorite))

        `when`(favoriteDao.getFavoriteTvShows()).thenReturn(flowOf(favorite.toList()))
        val result = repo.getFavoriteTvShow()
        assertEquals(favorite.map { it.toTvShow() }, result)
    }

    @Test
    fun test_fakeDao_insertFavorite() = runTest {
        val repo = ShowsRepositoryImp(api, FakeTvShowDao())
        val favorite = getShowsDetails()
        repo.markAsFavorite(favorite)
        assertEquals(favorite.toTvShow(), repo.getFavoriteTvShow().first().first())
    }

    @Test
    fun test_delete_favorite() = runTest {
        val repo = ShowsRepositoryImp(api, FakeTvShowDao())
        val favorite = getShowsDetails()
        repo.markAsFavorite(favorite)
        val list = repo.getFavoriteTvShow().first().first()
        assertEquals(favorite.toTvShow(), list)

        repo.deleteFromFavorite(favorite.id)
        assertEquals(emptyList<TvShow>(), repo.getFavoriteTvShow().last())
    }


    @Test
    fun test_getDetails() = runTest {
        val repo = ShowsRepositoryImp(api, favoriteDao)
        `when`(api.getShowById(1)).thenReturn(getTvShowDetailDto())
        val result = repo.getShowById(1)
        assertEquals(getTvShowDetailDto().toTvShowDetail(), result)
    }
}




private fun getTvShowDetailDto(): TvShowDetailDto {
    return TvShowDetailDto(
        _links =
        com.example.tvapp.data.remote.dto.details.Links(
            self = com.example.tvapp.data.remote.dto.details.Self(
                href = "d"
            ),
            previousepisode = Previousepisode(
                href = "fd"
            )
        ),
        averageRuntime = 60,
        dvdCountry = Any(),
        externals = Externals(
            imdb = "tt1553656",
            thetvdb = 264492,
            tvrage = 25988,
        ),
        image = com.example.tvapp.data.remote.dto.details.Image(
            medium = "TODO()",
            original = "TODO()"
        ),
        language = "TODO()",
        network = Network(
            country = Country(
                code = "TODO()",
                name = "TODO()",
                timezone = "TODO()"
            ),
            id = 1,
            name = "TODO()",
            officialSite = "TODO()"
        ),
        premiered = "TODO()",
        rating = com.example.tvapp.data.remote.dto.details.Rating(
            average = 6.5
        ),
        schedule = Schedule(
            days = listOf("fdf", "fds"),
            time = "TODO()"
        ),
        summary = "TODO()",
        updated = 21,
        webChannel = Any(),
        weight = 21,
        id = 1,
        ended = "2015-09-10",

        genres = listOf(
            "Drama",
            "Science-Fiction",
            "Thriller"
        ),
        name = "Under the Dom",
        officialSite = "TODO()",
        runtime = 60,
        status = "Ended",
        type = "Scripted",
        url = "TODO()"
    )
}

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

private fun episodes(): List<EpisodesDtoItem> {
    return listOf(
        EpisodesDtoItem(
            _links = Links(
                self = Self(
                    href = " "
                ),
                show = Show(
                    href = "TODO()",
                    name = "TODO()"
                )
            ),
            airdate = "2011-09-22",
            airstamp = "2011-09-23T01:00:00+00:00",
            airtime = "21:00",
            id = 28,
            image = com.example.tvapp.data.remote.dto.episodes.Image(
                medium = "TODO()",
                original = " ",
            ),
            name = "Pilot",
            number = 1,
            rating = Rating(
                average = 7.6
            ),
            runtime = 60,
            season = 1,
            summary = "\u003Cp\u003EJohn Reese is a former CIA operative living off the grid in NYC. When Reese is hauled into NYPD's 8th Precinct for a skirmish on the subway, he's bailed out by a man he doesn't know: Mr. Finch, a mysterious billionaire. Finch offers Reese a choice: stay on the streets and drink himself to death -- or join him in his mission to stop violent crimes before they happen. Finch has access to a secret source of information, one that identifies people who are about to be involved in violent crimes. There's just one catch: he doesn't know if these people will be the victims or the perpetrators. Skeptical but intrigued, Reese agrees to help. Together, Reese and Finch work to discover what danger is about to befall Assistant District Attorney Diane Hansen. Their actions garner the unwanted attention of NYPD homicide detective Carter -- a dedicated veteran cop who's not fond of vigilantes -- and Detective Fusco, a precinct detective who stands in the way of Reese's plans.\u003C/p\u003E",
            type = "regular",
            url = ""
        )
    )
}

private fun searchShows(): List<TvShowDto> {
    return listOf(
        TvShowDto(
            id = 3,
            name = "Bitten",
            language = "English",
            image = Image(
                medium = "TODO()",
                original = " "
            ),
            status = "Ended"
        )
    )
}