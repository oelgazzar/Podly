package com.example.podly.ui.home

import androidx.annotation.OptIn
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.compose.state.rememberErrorState
import com.example.podly.ui.utils.toUserMessage

@OptIn(UnstableApi::class)
@Composable
fun ErrorEffect(
    player: Player?,
    snackbarHostState: SnackbarHostState
) {
    val errorState = rememberErrorState(player)

    LaunchedEffect(errorState.error) {
        errorState.error?.cause?.let { cause ->
            val message = cause.toUserMessage()
            snackbarHostState.showSnackbar(
                cause.toString(),
                withDismissAction = true,
                duration = SnackbarDuration.Indefinite
            )
        }
    }
}