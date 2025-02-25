package com.example.tvapp.util

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.tvapp.presentation.ListOFTvShows.TvShowListScreenRoot
import com.example.tvapp.presentation.ListOFTvShows.TvShowsListViewModel
import com.example.tvapp.presentation.Route
import com.example.tvapp.presentation.SelectedEpisodeViewModel
import com.example.tvapp.presentation.TvShowDetail.TvShowDetailScreenRoot
import com.example.tvapp.presentation.TvShowDetail.TvShowDetailViewModel
import com.example.tvapp.presentation.TvShowDetail.episodes.EpisodeAction
import com.example.tvapp.presentation.TvShowDetail.episodes.EpisodesDetailScreenRoot
import com.example.tvapp.presentation.TvShowDetail.episodes.EpisodesViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.TvGraph
    ) {
        navigation<Route.TvGraph>(
            startDestination = Route.TvShowsList
        ) {
            composable<Route.TvShowsList>(
                exitTransition = { slideOutHorizontally() },
                popEnterTransition = { slideInHorizontally() }
            ) {
                val viewModel = hiltViewModel<TvShowsListViewModel>()
                TvShowListScreenRoot(
                    viewModel = viewModel,
                    onClickTvShow = { tvShow ->
                        navController.navigate(
                            Route.TvShowDetail(tvShow.id.toString())
                        )
                    }
                )
            }

            composable<Route.TvShowDetail>(
                enterTransition = {
                    slideInHorizontally { initialOffset ->
                        initialOffset
                    }
                },
                exitTransition = {
                    slideOutHorizontally { initialOffset ->
                        initialOffset
                    }
                }
            ) {
                val viewModel = hiltViewModel<TvShowDetailViewModel>()
                val selectedEpisodeViewModel =
                    it.sharedViewModel<SelectedEpisodeViewModel>(navController)

                LaunchedEffect(true) {
                    selectedEpisodeViewModel.onSelectedEpisode(null)
                }
                TvShowDetailScreenRoot(
                    viewModel = viewModel,
                    onBackClick = {
                        navController.navigateUp()
                    },
                    onEpisodeClick = { episode ->
                        selectedEpisodeViewModel.onSelectedEpisode(episode)//TODO navigation to episode
                    }
                )
            }

            composable<Route.EpisodeDetail> {
                val viewModel = hiltViewModel<EpisodesViewModel>()
                val selectedViewModel = it.sharedViewModel<SelectedEpisodeViewModel>(navController)
                val selectedEpisode by selectedViewModel.selectedEpisode.collectAsStateWithLifecycle()
                LaunchedEffect(selectedEpisode) {
                    selectedEpisode?.let {
                        viewModel.onAction(EpisodeAction.OnSelectedEpisodeChange(it))
                    }
                }

                EpisodesDetailScreenRoot(
                    viewModel = viewModel,
                    onBackClick = {
                        navController.navigateUp()
                    }
                )
            }
        }

    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}


//
//@Composable
//fun Navigation() {
//    val navController = rememberNavController()
//    NavHost(
//        navController = navController,
//        startDestination = Screen.ListOfTvShows.route
//    ) {
//        composable(route = Screen.ListOfTvShows.route) {
//            val viewModel = hiltViewModel<TvShowsListViewModel>()
//            val state by viewModel.state.collectAsStateWithLifecycle()
//
//            TvShowListScreen(
//                onNavigate =
//                {tvShow ->
//                    //navController.navigate((Screen.TvShowDetail.route +"id=${tvShow.route.}"))
//                    navController.navigate(Screen.TvShowDetail.route + "id=${tvShow.route} ")
//                },
//                state = state,
//                onEvent = viewModel::onEvent,
//                uiEvent = viewModel.uiEvent
//            )
//        }
//
//        composable(
//            route = Screen.TvShowDetail.route + "/{tvShowId}",
//        ) {navBackStackEntry ->
//            val argument = navBackStackEntry.arguments?.getString("tvShowId")
//            val viewModel = hiltViewModel<TvShowDetailViewModel>()
//            val state by viewModel.state.collectAsStateWithLifecycle()
//
//            TvShowDetailScreen(
//                onPopBackStack = { UiEvent.PopBackStack },
//                state = state,
//                onEvent = viewModel::onEvent,
//                uiEvent = viewModel.uiEvent
//            )
//        }
//    }
//}

//    val navController = rememberNavController()
//
//    NavHost(
//        navController = navController,
//        startDestination = Screen.ListOfTvShows.route
//    ) {
//        composable(
//            route = Screen.ListOfTvShows.route
//        ) {
//            //val viewModel: TvShowsListViewModel = hiltViewModel()
//            val viewModel = hiltViewModel<TvShowsListViewModel>()
//            val state by viewModel.state.collectAsStateWithLifecycle()
//
////            LaunchedEffect(key1 = true) {
////                viewModel.uiEvent.collect{ event ->
////                    when (event) {
////                        UiEvent.PopBackStack -> {
////                            navController.popBackStack()
////                        }
////                        is UiEvent.ShowSnackbar -> TODO()
////                        else -> Unit
////                    }
////                }
////            }
//
////            CollectFlowWithLifecycle(flow = viewModel.uiEvent) { event ->
////                when(event) {
////                    is UiEvent.Navigate -> {
////                        navController.navigate(
////                            Screen.TvShowDetail.route + "?tvshowId=${event.route}"
////                        )
////                    }
////                    is UiEvent.ShowSnackbar -> TODO()
////                    else -> Unit
////                }
////            }
//
//            TvShowListScreen(
//                onNavigate = {
//                    navController.navigate(Screen.TvShowDetail.route+"Id=${it.route}")//better
//                },
//                state = state,
//                onEvent = viewModel::onEvent,
//                uiEvent = viewModel.uiEvent
//            )
//        }
//        composable(
//            //route = Screen.TvShowDetail.route + "/{id}"
//            route = Screen.ListOfTvShows.route
//        ) {
//            val viewModel = hiltViewModel<TvShowDetailViewModel>()
//            val state by viewModel.state.collectAsStateWithLifecycle()
//
//
////            CollectFlowWithLifecycle(flow = viewModel.uiEvent) { event ->
////                when(event) {
////                    is UiEvent.Navigate -> {
////                        TODO()//
////                    }
////                    is UiEvent.PopBackStack -> {
////                        navController.popBackStack()
////                    }
////                    is UiEvent.ShowSnackbar -> {
////
////                    }
////                }
////            }
//
////            LaunchedEffect(key1 = true) {
////                viewModel.uiEvent.collect { event ->
////                    when (event) {
////                        is UiEvent.PopBackStack -> {
////                            navController.popBackStack()
////                        }
////                        is UiEvent.Navigate -> TODO()
////                        is UiEvent.ShowSnackbar -> TODO()
////                    }
////                }
////            }
//
//            TvShowDetailScreen(
//                onPopBackStack = {
//                    navController.popBackStack()
//                },
//                state = state,
//                onEvent = viewModel::onEvent,
//                uiEvent = viewModel.uiEvent
//            )
//        }
//    }
//
//}