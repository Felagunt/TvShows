package com.example.tvapp.presentation.TvShowDetail

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.internalToRoute
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
import com.example.tvapp.data.remote.mappers.toEpisode
import com.example.tvapp.data.remote.mappers.toTvShowDetail
import com.example.tvapp.domain.models.TvShow
import com.example.tvapp.domain.models.TvShowDetail
import com.example.tvapp.domain.repository.ShowsRepository
import com.example.tvapp.domain.use_case.GetTvShowsEpisodesUseCase
import com.example.tvapp.domain.use_case.get_tvShow.GetTvShowUseCase
import com.example.tvapp.presentation.Route
import com.example.tvapp.util.Resource
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class TvShowDetailViewModelTest {

    @get:Rule(order = 1)
    val savedStateHandleRule = SavedStateHandleRule(Route.TvShowDetail("1"))

    @get:Rule(order = 2)
    val mainDispatcherRule = MainDispatcherRule()

    private val getTvShowUseCase: GetTvShowUseCase = mock()
    private val getTvShowsEpisodesUseCase: GetTvShowsEpisodesUseCase = mock()
    private val repository: ShowsRepository = mock()
    private val savedStateHandle: SavedStateHandle = mock()

    @Test
    fun test_get_tvShow_detail_success() = runTest {//TODO
//        `when`(getTvShowUseCase.invoke(1))
//            .thenReturn(flowOf(Resource.Success(getShowsDetails())))
//        `when`(getTvShowsEpisodesUseCase.invoke(1))
//            .thenReturn(flowOf(Resource.Success(episodes().map { it.toEpisode() })))
//        `when`(repository.isTvShowFavorite(1)).thenReturn(flowOf(true))
        `when`(getTvShowUseCase.invoke(1))
            .thenReturn(flowOf(Resource.Success(getShowsDetails())))
        `when`(getTvShowsEpisodesUseCase.invoke(1))
            .thenReturn(flowOf(Resource.Success(episodes().map { it.toEpisode() })))
        val viewModel = TvShowDetailViewModel(
            getTvShowUseCase = getTvShowUseCase,
            getTvShowsEpisodesUseCase = getTvShowsEpisodesUseCase,
            repository = repository,
            savedStateHandle = savedStateHandleRule.savedStateHandleMock
        )
        //viewModel.onEvent(TvShowDetailEvent.OnAddFavoriteTvShow)
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect {}
        }
        assertEquals(getShowsDetails(), viewModel.state.value.tvShow)
    }

    @Test
    fun test_failure_tvShowDetails() = runTest {//TODO
        `when`(getTvShowUseCase.invoke(1))
            .thenReturn(flowOf(Resource.Error("error")))
        val viewModel = TvShowDetailViewModel(
            getTvShowUseCase = getTvShowUseCase,
            getTvShowsEpisodesUseCase = getTvShowsEpisodesUseCase,
            repository = repository,
            savedStateHandle = savedStateHandleRule.savedStateHandleMock
        )
        val tvShow = mutableStateOf<TvShowDetailState>(TvShowDetailState())
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collectLatest {
                tvShow.value.error = it.error
            }
        }
        assertEquals("error", tvShow.value.error)
        //assertEquals("error", viewModel.state.value.error)
    }

    @Test
    fun test_is_favorite() = runTest {//TODO
        val favoriteDb = mutableListOf<TvShowDetail>()
        val favorite = getTvShowDetailDto().toTvShowDetail()
        `when`(repository.markAsFavorite(favorite))
            .then {
                favoriteDb.add(favorite)
                flowOf(Unit)
            }
        `when`(repository.isTvShowFavorite(1))
            .then {
                favoriteDb.contains(favorite)
                flowOf(Unit)
            }
        `when`(repository.deleteFromFavorite(favorite.id))
            .then {
                favoriteDb.remove(favorite)
                flowOf(Unit)
            }
//        `when`(getTvShowUseCase.invoke(1))
//            .thenReturn(flowOf(Resource.Success(getShowsDetails())))
//        `when`(repository.isTvShowFavorite(1)).thenReturn(flowOf(true))
        val viewModel = TvShowDetailViewModel(
            getTvShowUseCase = getTvShowUseCase,
            getTvShowsEpisodesUseCase = getTvShowsEpisodesUseCase,
            repository = repository,
            savedStateHandle = savedStateHandleRule.savedStateHandleMock
        )
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect {}
        }
        viewModel.onEvent((TvShowDetailEvent.OnAddFavoriteTvShow))
        assert(favoriteDb.contains(favorite))
        //assertEquals(favorite, viewModel.state.value.tvShow)
        //assertEquals(true, viewModel.state.value.isFavorite)
    }

    class SavedStateHandleRule(
        private val route: Any,
    ) : TestWatcher() {
        val savedStateHandleMock: SavedStateHandle = mockk()
        override fun starting(description: Description?) {
            mockkStatic("androidx.navigation.SavedStateHandleKt")
            every { savedStateHandleMock.internalToRoute<Any>(any(), any()) } returns route
            super.starting(description)
        }

        override fun finished(description: Description?) {
            unmockkStatic("androidx.navigation.SavedStateHandleKt")
            super.finished(description)
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
}

class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description?) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        Dispatchers.resetMain()
    }
}
