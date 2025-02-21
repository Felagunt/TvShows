package com.example.tvapp.presentation.ListOFTvShows.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tvapp.presentation.ListOFTvShows.TvShowsEvent

@Composable
fun BottomBar(
    selected: Int,
    onAction: (TvShowsEvent) -> Unit
) {
    val selectedIndex = remember { mutableStateOf(selected) }
    NavigationBar {
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Home"
                )
            },
            label = {
                Text(
                    text = "Home"
                )
            },
            selected = selectedIndex.value == 0,
            onClick = {
                selectedIndex.value = 0
                onAction(TvShowsEvent.OnTabSelected(0))
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite"
                )
            },
            label = {
                Text(
                    text = "Favorite"
                )
            },
            selected = selectedIndex.value == 1,
            onClick = {
                selectedIndex.value = 1
                onAction(TvShowsEvent.OnTabSelected(1))
            }
        )
    }
}