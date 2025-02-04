package com.example.tvapp.presentation.ListOFTvShows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvapp.domain.use_case.get_tvShows.GetTvShowsListUseCase
import com.example.tvapp.presentation.Screen
import com.example.tvapp.presentation.UiEvent
import com.example.tvapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowsListViewModel @Inject constructor(
    private val getTvShowsListUseCase: GetTvShowsListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TvShowsListState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getTvShows()
    }

    fun onEvent(event: TvShowsEvent) {
        when (event) {
            is TvShowsEvent.OnAddFavoriteTvShow -> {
                //TODO
                sendUiEvent(UiEvent.ShowSnackbar(
                    message = "Added to favorite"
                ))
            }

            is TvShowsEvent.OnTvShowClick -> {
                sendUiEvent(
                    UiEvent.Navigate(event.tvShow.id.toString())
//                    UiEvent.Navigate(
////                        event.tvShow.id.toString()
//                        Screen.TvShowDetail.route + "?tvshowId=${event.tvShow.id}"
//                    )
                )
            }

            TvShowsEvent.OnRefresh -> {
                getTvShows()
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun getTvShows() {
        getTvShowsListUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        tvShows = result.data ?: emptyList(),
                        isLoading = false
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = result.message ?: "An unknown error"
                    )
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}
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