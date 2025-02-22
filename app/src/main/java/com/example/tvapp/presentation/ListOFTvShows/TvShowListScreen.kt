package com.example.tvapp.presentation.ListOFTvShows

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tvapp.domain.models.TvShow
import com.example.tvapp.presentation.ListOFTvShows.components.EmbeddedSearchBar
import com.example.tvapp.presentation.ListOFTvShows.components.TvShowListItem
import com.example.tvapp.presentation.ListOFTvShows.components.TvShowSearchBar

@Composable
fun TvShowListScreenRoot(
    viewModel: TvShowsListViewModel = hiltViewModel(),
    onClickTvShow: (TvShow) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    TvShowListScreen(
        state = state,
        onEvent = { tvShowsEvent ->
            when (tvShowsEvent) {
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

    val keyboardController = LocalSoftwareKeyboardController.current

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val pagerState = rememberPagerState { 2 }

    LaunchedEffect(state.selectedIndex) {
        pagerState.animateScrollToPage(state.selectedIndex)
    }

    LaunchedEffect(pagerState.currentPage) {
        onEvent(TvShowsEvent.OnTabSelected(pagerState.currentPage))
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


    Scaffold(
//        bottomBar = {
//            BottomBar(
//                selected = state.selectedIndex,
//                onAction = {
//                    onEvent(TvShowsEvent.OnTabSelected(state.selectedIndex))
//                }
//            )
//        }
        topBar = {
            TvShowSearchBar(
                searchQuery = state.searchQuery ?: "",
                onSearchQueryChange = {
                    onEvent(TvShowsEvent.OnSearchQueryChange(it))
                },
                onImeSearch = {
                    keyboardController?.hide()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(top = 4.dp)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(paddingValues = padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TabRow(
                selectedTabIndex = state.selectedIndex,
                modifier = Modifier
                    //.padding(vertical = 12.dp)
                    //.padding(top = padding.calculateTopPadding())
                    .fillMaxWidth()
            ) {
                Tab(
                    selected = state.selectedIndex == 0,
                    onClick = {
                        onEvent(TvShowsEvent.OnTabSelected(0))
                    },
                    modifier = Modifier.weight(1f),
                    selectedContentColor = MaterialTheme.colorScheme.onSurface,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                ) {
                    Text(
                        text = "Home",
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                    )
                }
                Tab(
                    selected = state.selectedIndex == 1,
                    onClick = {
                        onEvent(TvShowsEvent.OnTabSelected(1))
                    },
                    modifier = Modifier.weight(1f),
                    selectedContentColor = MaterialTheme.colorScheme.onSurface,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                ) {
                    Text(
                        text = "Favorite",
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        end = 20.dp,
                        top = 15.dp + padding.calculateTopPadding(),
                        bottom = 15.dp + padding.calculateBottomPadding()
                    )
                ) {

                    if (state.selectedIndex == 0) {
                        item {
                            Text(
                                text = "Tv shows",
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                        items(
                            state.tvShows,
                            key = { it.id }
                        ) { tvShow ->
                            TvShowListItem(
                                tvShow = tvShow,
                                //OnNavigateToTvShow ={},
                                modifier = Modifier
                                    .clickable {
                                        onEvent(TvShowsEvent.OnTvShowClick(tvShow))
                                    }
                                    .padding(top = 8.dp, bottom = 8.dp)
                            )
                        }
                    } else {
                        item {
                            Text(text = "favorite", style = MaterialTheme.typography.titleLarge)
                        }
                        items(
                            state.favoriteTvShows
                        ) { favorite ->
                            TvShowListItem(
                                tvShow = favorite,
                                modifier = Modifier
                                    .clickable {
                                        onEvent(TvShowsEvent.OnTvShowClick(favorite))
                                    }
                                    .padding(top = 8.dp, bottom = 8.dp)
                            )
                        }
                    }
                }

            }
        }
    }
}