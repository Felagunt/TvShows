package com.example.tvapp.presentation

import androidx.lifecycle.ViewModel
import com.example.tvapp.domain.models.Episode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SelectedEpisodeViewModel @Inject constructor() : ViewModel() {

    private val _selectedEpisode = MutableStateFlow<Episode?>(null)
    val selectedEpisode = _selectedEpisode.asStateFlow()

    fun onSelectedEpisode(episode: Episode?) {
        _selectedEpisode.value = episode
    }
}