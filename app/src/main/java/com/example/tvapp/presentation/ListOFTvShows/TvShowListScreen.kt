package com.example.tvapp.presentation.ListOFTvShows

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tvapp.domain.models.TvShow
import com.example.tvapp.presentation.ListOFTvShows.components.TvShowListItem
import com.example.tvapp.presentation.TvShowDetail.TvShowDetailScreen
import com.example.tvapp.presentation.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun TvShowListScreenRoot(
    viewModel: TvShowsListViewModel = hiltViewModel(),
    onClickTvShow: (TvShow) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    TvShowListScreen(
        state = state,
        onEvent = { tvShowsEvent ->
            when(tvShowsEvent) {
                is TvShowsEvent.OnTvShowClick -> onClickTvShow(tvShowsEvent.tvShow)
                else -> Unit
            }
            viewModel.onEvent(tvShowsEvent)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvShowListScreen(
    state: TvShowsListState,
    onEvent: (TvShowsEvent) -> Unit
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

//    LaunchedEffect(key1 = true) {
//        uiEvent.collect { event ->
//            when(event) {
//                is UiEvent.ShowSnackbar -> {
//                    val result = snackbarHostState.showSnackbar(
//                        message = event.message,
//                        actionLabel = event.action
//                    )
//                    if(result == SnackbarResult.ActionPerformed) {
//                        onEvent(TvShowsEvent.OnAddFavoriteTvShow)//TODO
//                    }
//                }
////                is UiEvent.Navigate -> {
////                    onNavigate(event)
////                }
//                else -> Unit
//            }
//        }
//    }
    

    Scaffold { padding ->
        LazyColumn(
            contentPadding = PaddingValues(
                start = 20.dp,
                end = 20.dp,
                top = 15.dp + padding.calculateTopPadding(),
                bottom = 15.dp + padding.calculateBottomPadding()
            )
        ) {
            item {
                Text(
                    text = "Tv shows",
                    style = MaterialTheme.typography.titleLarge
                )
            }
            items(state.tvShows) {tvShow ->
                TvShowListItem(
                    tvShow = tvShow,
                    //OnNavigateToTvShow ={},
                    modifier = Modifier
                        .clickable {
                            onEvent(TvShowsEvent.OnTvShowClick(tvShow))
                        }
                )
            }
        }
    }
}