package com.example.tvapp.presentation.TvShowDetail

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
    //object PopBackStack: UiEvent()
}