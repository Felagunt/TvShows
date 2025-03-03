package com.example.tvapp.presentation.episodes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.tvapp.domain.models.Episode
import com.example.tvapp.domain.models.TvShow
import com.example.tvapp.domain.repository.ShowsRepository
import com.example.tvapp.domain.use_case.GetTvShowsEpisodesUseCase
import com.example.tvapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getTvShowsEpisodesUseCase: GetTvShowsEpisodesUseCase,
    //private val repository: ShowsRepository
) : ViewModel() {

    //private val episodeId = savedStateHandle.toRoute<Episode>().id

    private val showId = savedStateHandle.toRoute<TvShow>().id

    private val _state = MutableStateFlow(EpisodeState())
    val state = _state.onStart {
        if(_state.value.episode == null){
            getTvShowsEpisodes(showId,1)
        } else {
            _state.value.episode?.id?.let {episodeId ->
                getTvShowsEpisodes(showId, episodeId) }
        }
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state
        )
//
//    private val _state = MutableStateFlow(TvShowDetailState())
//    val state = _state.onStart {
//        getTvShow(tvShowId)
//        observeFavoriteStatus()
//        getTvShowsEpisodes(tvShowId)
//    }
//        .stateIn(
//            viewModelScope,
//            SharingStarted.WhileSubscribed(5000L),
//            _state.value
//        )


    fun onAction(action: EpisodeAction) {
        when (action) {
            is EpisodeAction.OnNavigateBackEpisode -> {

            }
            is EpisodeAction.OnSelectedEpisodeChange -> {
                _state.update {
                    it.copy(
                        episode = action.episode
                    )
                }
//                viewModelScope.launch {
//                    getTvShowsEpisodes(showId = showId, action.episode.id)
//                }
            }
        }
    }

    private fun getTvShowsEpisodes(showId: Int, episodeId: Int) {
        getTvShowsEpisodesUseCase(showId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            episode = result.data?.firstOrNull { it.id == episodeId },
                            isLoading = false
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            errorMsg = result.message ?: "An unknown error"
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
//
//    private fun getEpisode() = flow<EpisodeState> {//TODO
//        val result = repository.getTvShowsEpisodes(showId)
//            .firstOrNull { it.id == _state.value.episode?.id }
//
//
//        _state.update {
//            it.copy(
//                episode = result
//            )
//        }
//    }
}

