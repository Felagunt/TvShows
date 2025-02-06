package com.example.tvapp.presentation.TvShowDetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.tvapp.presentation.TvShowDetail.TvShowDetailState

@Composable
fun TvRow(
    state: TvShowDetailState,
    modifier: Modifier = Modifier
) {
    state.tvShow?.let {


        Column (
            modifier = modifier
                .fillMaxWidth(),
                //.background(MaterialTheme.colorScheme.inverseSurface),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Lang: ${state.tvShow.language}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "status: ${state.tvShow.status}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "premiered: ${state.tvShow.premiered}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

}