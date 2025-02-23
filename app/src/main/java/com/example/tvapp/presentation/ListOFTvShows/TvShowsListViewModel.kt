package com.example.tvapp.presentation.ListOFTvShows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvapp.domain.repository.ShowsRepository
import com.example.tvapp.domain.use_case.get_tvShows.GetTvShowsListByNameUseCase
import com.example.tvapp.domain.use_case.get_tvShows.GetTvShowsListUseCase
import com.example.tvapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowsListViewModel @Inject constructor(
    private val getTvShowsListUseCase: GetTvShowsListUseCase,
    private val getTvShowsListByNameUseCase: GetTvShowsListByNameUseCase,
    private val repository: ShowsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TvShowsListState())
    val state = _state
        .onStart {
            getTvShows()
            observeFavoriteTvShow()
            observeSearchTvShow()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private var observeFavoriteJob: Job? = null
    private var searchJob: Job? = null

//    val state = _state.asStateFlow()
//
//    private val _uiEvent = Channel<UiEvent>()
//    val uiEvent = _uiEvent.receiveAsFlow()
//
//    init {
//        getTvShows()
//    }

    fun onEvent(event: TvShowsEvent) {
        when (event) {
            is TvShowsEvent.OnAddFavoriteTvShow -> {
                //TODO add repo and collect from there

            }

            is TvShowsEvent.OnRefresh -> {
                //TODO later

            }

            is TvShowsEvent.OnTvShowClick -> {
                _state.update {
                    it.copy()
                }

            }

            is TvShowsEvent.OnSearchQueryChange -> {
                _state.update {
                    it.copy(
                        searchQuery = event.query
                    )
                }
            }

            is TvShowsEvent.OnTabSelected -> {
                _state.update {
                    it.copy(
                        selectedIndex = event.index
                    )
                }
            }
        }
    }

    private fun observeSearchTvShow() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                //searchResults = _state.value.tvShows,
                                error = null
                            )
                        }
                    }
                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = searchTvShow(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchTvShow(query: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        getTvShowsListByNameUseCase(name = query)
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = null,
                                searchResults = result.data!!
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                searchResults = emptyList(),
                                isLoading = false,
                                error = result.message ?: "An unknown error"
                            )

                        }
                    }

                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }
                }
            }
    }


    private fun observeFavoriteTvShow() {
        observeFavoriteJob?.cancel()
        observeFavoriteJob = repository
            .getFavoriteTvShow()
            .onEach { favorite ->
                _state.update {
                    it.copy(
                        favoriteTvShows = favorite
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun getTvShows() {
        getTvShowsListUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            tvShows = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            error = result.message ?: "An unknown error"
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}


//    fun onEvent(event: TvShowsEvent) {
//        when (event) {
//            is TvShowsEvent.OnAddFavoriteTvShow -> {
//                //TODO
//                sendUiEvent(
//                    UiEvent.ShowSnackbar(
//                        message = "Added to favorite"
//                    )
//                )
//            }
//
//            is TvShowsEvent.OnTvShowClick -> {
//                sendUiEvent(
//                    UiEvent.Navigate(
//                        Screen.TvShowDetail.route + "?tvShowId="$ { event.tvShow.id.toString() }")
////                    UiEvent.Navigate(
////                        event.tvShow.id.toString()
////                        Screen.TvShowDetail.route + "?tvshowId=${event.tvShow.id}"
////                    )
//                )
//            }
//
//            TvShowsEvent.OnRefresh -> {
//                getTvShows()
//            }
//        }
//    }

//    private fun sendUiEvent(event: UiEvent) {
//        viewModelScope.launch {
//            _uiEvent.send(event)
//        }
//    }
/*
package com.example.jetpack.viewmodels
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack.datamodels.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class BookViewModel(appObj: Application) : AndroidViewModel(appObj) {
    private val _bookList =
        MutableStateFlow(Result(state = State.Loading, categoryWithBooks = listOf()))
    val bookList: StateFlow get() = _bookList init { fetchBooks() }
    private fun fetchBooks() {
        viewModelScope.launch {
            try {
                val call = RestClient().getService().fetchBookList()
                val response = call?.awaitResponse()
                if (response?.isSuccessful == true) {
                    val getResponse = response.body()
                    if (getResponse?.success == 1L) {
                        val list = mutableListOf()
                        val categories = mutableMapOf()
                        getResponse.booklist.forEach {
                            categories[it.categoryId] = it.categoryName
                        }
                        categories.forEach { map: Map.Entry ->
                            val books = getResponse.booklist.filter { it.categoryId == map.key }
                                .toMutableList()
                            books.sortBy { it.title } list . add (CategoryWithBooks(
                                map.key,
                                map.value,
                                books
                            ))
                        }
                        list.sortBy { it.name } _bookList . emit (Result(
                            state = State.Success,
                            categoryWithBooks = list
                        ))
                    } else {
                        _bookList.emit(Result(state = State.Failed, categoryWithBooks = listOf()))
                    }
                } else {
                    _bookList.emit(Result(state = State.Failed, categoryWithBooks = listOf()))
                }
            } catch (e: Exception) {
                Log.e(
                    "BookViewModel",
                    e.message ?: "",
                    e
                ) _bookList . emit (Result(state = State.Failed, categoryWithBooks = listOf()))
            }
        }
    }
}

data class Result(val state: State, val categoryWithBooks: List)
enum class State { Success, Failed, Loading }
*/