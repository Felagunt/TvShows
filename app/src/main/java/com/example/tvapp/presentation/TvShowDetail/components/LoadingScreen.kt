package com.example.tvapp.presentation.TvShowDetail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tvapp.presentation.TvShowDetail.TvShowDetailState

@Composable
fun LoadingScreen(
    state: TvShowDetailState,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
            Spacer(modifier = modifier.height(8.dp))
                Text(text = "Loading", style = MaterialTheme.typography.headlineMedium)
        }
    }
}