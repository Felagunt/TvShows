package com.example.tvapp.presentation.TvShowDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.tvapp.domain.repository.ShowsRepository
import com.example.tvapp.domain.use_case.GetTvShowsEpisodesUseCase
import com.example.tvapp.domain.use_case.get_tvShow.GetTvShowUseCase
import com.example.tvapp.presentation.Route
import com.example.tvapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowDetailViewModel @Inject constructor(
    private val getTvShowUseCase: GetTvShowUseCase,
    private val getTvShowsEpisodesUseCase: GetTvShowsEpisodesUseCase,
    private val repository: ShowsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val tvShowId = savedStateHandle.toRoute<Route.TvShowDetail>().id.toInt()

    private val _state = MutableStateFlow(TvShowDetailState())
    val state = _state.onStart {
        getTvShow(tvShowId)
        observeFavoriteStatus()
        getTvShowsEpisodes(tvShowId)
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onEvent(event: TvShowDetailEvent) {
        when (event) {
            is TvShowDetailEvent.OnAddFavoriteTvShow -> {
                //TODO

                viewModelScope.launch {
                    if (state.value.isFavorite) {
                        repository.deleteFromFavorite(tvShowId)
                    } else {
                        state.value.tvShow?.let { tvShow ->
                            repository.markAsFavorite(tvShow)
                        }
                    }
                }
            }

            is TvShowDetailEvent.OnNavigationBack -> {
                //sendUiEvent(UiEvent.PopBackStack)
            }
            is TvShowDetailEvent.OnEpisodeClick -> {

            }

            else -> {}
        }
    }

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

    private fun getTvShowsEpisodes(id: Int) {
        getTvShowsEpisodesUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            episodes = result.data,
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


    private fun observeFavoriteStatus() {
        repository
            .isTvShowFavorite(tvShowId)
            .onEach { isFavorite ->
                _state.update {
                    it.copy(
                        isFavorite = isFavorite
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}