package com.example.tvapp.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tvapp.presentation.ListOFTvShows.TvShowListScreen
import com.example.tvapp.presentation.ListOFTvShows.TvShowsListViewModel
import com.example.tvapp.presentation.Screen
import com.example.tvapp.presentation.TvShowDetail.TvShowDetailScreen
import com.example.tvapp.presentation.TvShowDetail.TvShowDetailViewModel
import com.example.tvapp.presentation.UiEvent

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.ListOfTvShows.route
    ) {
        composable(route = Screen.ListOfTvShows.route) {
            val viewModel = hiltViewModel<TvShowsListViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            TvShowListScreen(
                onNavigate =
                {tvShow ->
                    navController.navigate(Screen.TvShowDetail.route + "{${tvShow.route}}")
                },
                state = state,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent
            )
        }

        composable(route = Screen.TvShowDetail.route) {

        }
    }
}

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