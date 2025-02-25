package com.example.tvapp.presentation.TvShowDetail.episodes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.tvapp.domain.models.Episode
import com.example.tvapp.domain.repository.ShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    //savedStateHandle: SavedStateHandle,
    private val repository: ShowsRepository
) : ViewModel() {

    //private val episodeId = savedStateHandle.toRoute<Episode>().id

    //private val showId: Int? = null

    private val _state = MutableStateFlow(EpisodeState())
    val state = _state.onStart {
        //getEpisode()
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
            }
        }
    }
//
//    private fun getEpisode() = flow<EpisodeState> {//TODO
//        repository.getTvShowsEpisodes(episodeId)
//
//
//        _state.update {
//            it.copy(
//                episode = null
//            )
//        }
//    }
}

