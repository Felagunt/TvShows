package com.example.tvapp.presentation.TvShowDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.tvapp.domain.use_case.get_tvShow.GetTvShowUseCase
import com.example.tvapp.presentation.Route
import com.example.tvapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TvShowDetailViewModel @Inject constructor(
    private val getTvShowUseCase: GetTvShowUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val tvShowId = savedStateHandle.toRoute<Route.TvShowDetail>().id.toInt()

    private val _state = MutableStateFlow(TvShowDetailState())
    val state = _state.
            onStart {
                getTvShow(tvShowId)
            }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

//    private val _uiEvent = Channel<UiEvent>()
//    val uiEvent = _uiEvent.receiveAsFlow()
//
//    init {
//        savedStateHandle.get<Int>("id")?.let { id ->
//                    getTvShow(id)
//        }
//    }

    fun onEvent(event: TvShowDetailEvent) {
        when (event) {
            is TvShowDetailEvent.OnAddFavoriteTvShow -> {
                //TODO
            }

            is TvShowDetailEvent.OnNavigationBack -> {
                //sendUiEvent(UiEvent.PopBackStack)
            }

            else -> {}
        }
    }

//    private fun sendUiEvent(event: UiEvent) {
//        viewModelScope.launch {
//            _uiEvent.send(event)
//        }
//    }

    private fun getTvShow(id: Int) {
        getTvShowUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            tvShow = result.data,
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