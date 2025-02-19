package com.example.tvapp.presentation.TvShowDetail

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tvapp.presentation.Route
import com.example.tvapp.presentation.TvShowDetail.components.ContentScreen
import com.example.tvapp.presentation.TvShowDetail.components.ErrorScreen
import com.example.tvapp.presentation.TvShowDetail.components.LoadingScreen
import com.example.tvapp.presentation.UiEvent
import com.example.tvapp.util.CollectFlowWithLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun TvShowDetailScreenRoot(
    viewModel: TvShowDetailViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    TvShowDetailScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is TvShowDetailEvent.OnNavigationBack -> onBackClick()
                else -> Unit
            }
            viewModel.onEvent(event)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun TvShowDetailScreen(
    state: TvShowDetailState,
    onEvent: (TvShowDetailEvent) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()


//    CollectFlowWithLifecycle(flow = uiEvent) { event ->
//        when (event) {
//            is UiEvent.Navigate -> {
//
//            }
//
//            is UiEvent.PopBackStack -> {
//                onEvent(TvShowDetailEvent.OnNavigationBack)
//            }
//
//            is UiEvent.ShowSnackbar -> {
//                val result = snackbarHostState.showSnackbar(
//                    message = event.message,
//                    actionLabel = event.action
//                )
//                if (result == SnackbarResult.ActionPerformed) {
//                    //onEvent(TvShowDetailEvent.OnAddFavoriteTvShow)//TODO
//                }
//            }
//
//            else -> {}
//        }
//    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = state.tvShow?.name ?: "Detail")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onEvent(TvShowDetailEvent.OnNavigationBack)
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        onEvent(TvShowDetailEvent.OnAddFavoriteTvShow)
                    }) {
                        Icon(
                            imageVector = //Icons.Default.FavoriteBorder,
                            if(state.isFavorite) {
                                Icons.Filled.Favorite
                            } else {
                                Icons.Outlined.FavoriteBorder
                            },
                            tint = MaterialTheme.colorScheme.surfaceTint,
                            contentDescription = //"Add to favorite"
                            if(state.isFavorite) {
                                "Add to favorite"
                            } else {
                                "Remove from favorite"
                            }
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        ContentScreen(
            state = state,
            Modifier.padding(paddingValues)
                .verticalScroll(rememberScrollState())
        )
        if (state.error.isNotBlank()) {
            ErrorScreen(
                state = state,
                modifier = Modifier.padding()
            )
        }
        if (state.isLoading) {
            LoadingScreen(
                state = state,
                modifier = Modifier
                    .padding(paddingValues)
            )
        }

//            val id: Int,
//            val ended: String,
//            val imdb: String, r
//            val thetvdb: Int, r
//            val tvrage: Int,  r
//            val genres: List<String>, f
//            val name: String, t
//            val image: String, c
//            val originalImg: String,
//            val language: String, tr
//            val officialSite: String, o
//            val premiered: String, tr
//            val rating: Double, r
//            val runtime: Int,
//            val status: String, tr
//            val summary: String, s
//            val type: String,
//            val url: String s
    }
}