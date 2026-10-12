package com.example.podly.ui.home

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import com.example.podly.models.Track
import com.example.podly.ui.theme.AppTheme

@Composable
fun HomeScreen() {
    var currentTrack by remember { mutableStateOf(null as Track?) }
    val player = rememberPlayer(currentTrack?.uri)
    var playerExpanded by remember { mutableStateOf(false) }

    BackHandler(currentTrack != null) {
        currentTrack = null
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                currentTrack != null,
                enter = fadeIn() + slideInVertically { it },
                exit = fadeOut() + slideOutVertically { it }
            ) {
                MiniPlayer(
                    currentTrack?.title ?: "",
                    player,
                    onExpanded = { playerExpanded = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    ) { innerPadding ->
        HomeContent(
            currentTrack?.uri,
            setTrack = { currentTrack = it },
            player,
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        )

    }
    if (playerExpanded) {
        Surface {
            ExpandedPlayer(
                currentTrack?.title ?: "",
                player,
                onMinimized = { playerExpanded = false },
                modifier = Modifier.fillMaxSize()
                    .padding(16.dp)
            )
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
fun HomeContent(
    currentTrackUri: Uri?,
    setTrack: (Track) -> Unit,
    player: Player?,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }

    ErrorEffect(player, snackbarHostState)

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
    ) {
        TrackList(
            currentTrackUri = currentTrackUri,
            setCurrentTrack = setTrack,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        )
    }

    SnackbarHost(
        snackbarHostState,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
    )
}

@Preview
@Composable
private fun HomeContentPrev() {
    AppTheme {
        HomeContent(
            null,
            setTrack = {},
            null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
private fun HomeScreenPrev() {
    AppTheme {
        HomeScreen()
    }
}