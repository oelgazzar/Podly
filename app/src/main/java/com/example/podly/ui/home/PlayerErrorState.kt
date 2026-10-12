package com.example.podly.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.media3.common.Player
import androidx.media3.common.listenTo
import androidx.media3.datasource.HttpDataSource
import kotlinx.coroutines.launch

@Composable
fun rememberPlayerErrorState(
    player: Player?,
): String? {
    val coroutineScope = rememberCoroutineScope()
    var error by remember { mutableStateOf(null as String?) }

    LaunchedEffect(player) {
        coroutineScope.launch {
            player?.listenTo(Player.EVENT_PLAYER_ERROR) {
                val e = playerError?:return@listenTo
                val cause = e.cause
                if (cause is HttpDataSource.HttpDataSourceException) {
                    error = "Poor internet connection"
                } else {
                    error = "Unknown error"
                }
            }
        }
    }

    return error
}