package com.example.podly

import android.net.Uri
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.media3.common.Player
import androidx.navigation.compose.rememberNavController
import com.example.podly.models.Episode
import com.example.podly.ui.home.ErrorEffect
import com.example.podly.ui.home.MiniPlayer
import com.example.podly.ui.home.rememberPlayer
import com.example.podly.ui.navigation.AppNavHost

val LocalPlayer = compositionLocalOf<Player?> { null }

@Composable
fun App() {
    var currentEpisode by remember { mutableStateOf(null as Episode?) }
    val player = rememberPlayer(currentEpisode?.audioUrl?.toUri())
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()

    ErrorEffect(player, snackbarHostState)

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                true,
                enter = fadeIn() + slideInVertically { it },
                exit = fadeOut() + slideOutVertically { it }
            ) {
                MiniPlayer(
                    currentEpisode?.title ?: "",
                    player,
                    onExpanded = {  },
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
        CompositionLocalProvider(
            LocalPlayer provides player
        ) {
            AppNavHost(
                player = player,
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}