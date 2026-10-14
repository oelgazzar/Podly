package com.example.podly

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.Player
import androidx.navigation.compose.rememberNavController
import com.example.podly.playback.PlayerViewModel
import com.example.podly.ui.home.MiniPlayer
import com.example.podly.ui.home.rememberPlayer
import com.example.podly.ui.navigation.AppNavHost

val LocalPlayer = compositionLocalOf<Player?> { null }

@Composable
fun App() {
    val player = rememberPlayer()
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()
    val viewModel: PlayerViewModel = hiltViewModel()

    val playerState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentEpisode by viewModel.currentEpisode.collectAsStateWithLifecycle()

//    ErrorEffect(player, snackbarHostState)

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                true,
                enter = fadeIn() + slideInVertically { it },
                exit = fadeOut() + slideOutVertically { it }
            ) {
                MiniPlayer(
                    playerState,
                    currentEpisode,
                    playPause = { viewModel.onPlayPause(currentEpisode) },
                    seekTo = viewModel::onSeek,
                    {},
                    {},
                    {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        },
        snackbarHost = {
            SnackbarHost(
                snackbarHostState,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize()
            )
        }
    ) { innerPadding ->
            AppNavHost(
                currentEpisode = currentEpisode,
                isPlaying = playerState.isPlaying,
                playPause = viewModel::onPlayPause,
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
    }
}