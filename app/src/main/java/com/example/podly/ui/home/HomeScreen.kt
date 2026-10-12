package com.example.podly.ui.home

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.podly.ui.theme.AppTheme

@Composable
fun HomeScreen() {
    HomeContent(modifier = Modifier
        .safeDrawingPadding()
        .padding(16.dp))
}

@Composable
fun HomeContent(modifier: Modifier = Modifier) {
    var currentTrackUri by remember { mutableStateOf(null as Uri?) }
    val player = rememberPlayer(currentTrackUri)
    val playerError = rememberPlayerErrorState(player)
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(playerError) {
        playerError?.let {
            snackbarHostState.showSnackbar(it, withDismissAction = true)
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
    ) {
        TrackList(
            currentTrackUri,
            setTrackUri = { currentTrackUri = it },
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        )
        PodcastPlayerUI(player, modifier = Modifier.fillMaxWidth())
    }

    SnackbarHost(snackbarHostState,
        modifier = Modifier.fillMaxSize()
            .wrapContentSize())
}

@Preview
@Composable
private fun HomeContentPrev() {
    AppTheme {
        HomeContent()
    }
}