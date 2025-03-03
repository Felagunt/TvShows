package com.example.tvapp.presentation.ListOFTvShows

import com.example.tvapp.domain.models.TvShow
import com.example.tvapp.domain.repository.ShowsRepository
import com.example.tvapp.domain.use_case.get_tvShows.GetTvShowsListByNameUseCase
import com.example.tvapp.domain.use_case.get_tvShows.GetTvShowsListUseCase
import com.example.tvapp.util.Resource
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.Mockito.`when`

class TvShowsListViewModelTest {

    @get:Rule(order = 1)
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun before() {

    }

    @After
    fun after() {

    }

    private val getTvShowsListByNameUseCase: GetTvShowsListByNameUseCase = mockk()
    private val getTvShowsListUseCase: GetTvShowsListUseCase = mockk()
    private val repository: ShowsRepository = mockk()


    @Test
    fun test_search_success() = runTest {
        `when`(getTvShowsListByNameUseCase.invoke("bitten"))
            .thenReturn(flowOf(Resource.Success(data = searchShows())))
        val viewModel = TvShowsListViewModel(
            getTvShowsListUseCase = getTvShowsListUseCase,
            getTvShowsListByNameUseCase = getTvShowsListByNameUseCase,
            repository = repository
        )
        viewModel.onEvent(TvShowsEvent.OnSearchQueryChange("bitten"))

        assertEquals(searchShows(), viewModel.state.value.searchResults)
    }


    @Test
    fun test_search_failure() = runTest {//TODO
        `when`(getTvShowsListByNameUseCase.invoke("bitten"))
            .thenReturn(flowOf(Resource.Error(message = "error")))
        val viewModel = TvShowsListViewModel(
            getTvShowsListUseCase = getTvShowsListUseCase,
            getTvShowsListByNameUseCase = getTvShowsListByNameUseCase,
            repository = repository
        )
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.state.collect {}
        }
        viewModel.onEvent(TvShowsEvent.OnSearchQueryChange("bitten"))
        // Create an empty collector for the StateFlow
        assertEquals("error", viewModel.state.value.error)
    }

    @Test
    fun test_success() = runTest {
        `when`(getTvShowsListUseCase.invoke())
            .thenReturn(flowOf(Resource.Success(data = searchShows())))
        val viewModel = TvShowsListViewModel(
            getTvShowsListUseCase = getTvShowsListUseCase,
            getTvShowsListByNameUseCase = getTvShowsListByNameUseCase,
            repository = repository
        )

        assertEquals(searchShows(), viewModel.state.value.tvShows)
    }


    @Test
    fun test_failure() = runTest {//TODO
        `when`(getTvShowsListUseCase.invoke())
            .thenReturn(flowOf(Resource.Error(message = "error")))
        val viewModel = TvShowsListViewModel(
            getTvShowsListUseCase = getTvShowsListUseCase,
            getTvShowsListByNameUseCase = getTvShowsListByNameUseCase,
            repository = repository
        )

        assertEquals("error", viewModel.state.value.error)
    }

    @Test
    fun test_tvShowClick() = runTest {
        val viewModel = TvShowsListViewModel(
            getTvShowsListUseCase = getTvShowsListUseCase,
            getTvShowsListByNameUseCase = getTvShowsListByNameUseCase,
            repository = repository
        )
        viewModel.onEvent(TvShowsEvent.OnTvShowClick(searchShows().last()))
        assertEquals(searchShows().last(), viewModel.state.value.tvShows.last())
    }

    @Test
    fun test_onTab_selected() = runTest {//TODO
        val viewModel = TvShowsListViewModel(
            getTvShowsListUseCase = getTvShowsListUseCase,
            getTvShowsListByNameUseCase = getTvShowsListByNameUseCase,
            repository = repository
        )
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect {}
        }
        viewModel.onEvent(TvShowsEvent.OnTabSelected(0))
        assertEquals(0, viewModel.state.value.selectedIndex)

        viewModel.onEvent(TvShowsEvent.OnTabSelected(1))
        assertEquals(1, viewModel.state.value.selectedIndex)
    }

    @Test
    fun test_search_query() = runTest {//TODO
        `when`(getTvShowsListByNameUseCase.invoke("bitten"))
            .thenReturn(flowOf(Resource.Success(data = searchShows())))
        val viewModel = TvShowsListViewModel(
            getTvShowsListUseCase = getTvShowsListUseCase,
            getTvShowsListByNameUseCase = getTvShowsListByNameUseCase,
            repository = repository
        )
        viewModel.onEvent(TvShowsEvent.OnSearchQueryChange("bitten"))
        assertEquals("bitten", viewModel.state.value.searchQuery)
    }


    class MainDispatcherRule(val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()) :
        TestWatcher() {
        override fun starting(description: Description?) {
            Dispatchers.setMain(testDispatcher)
        }

        override fun finished(description: Description?) {
            Dispatchers.resetMain()
        }

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