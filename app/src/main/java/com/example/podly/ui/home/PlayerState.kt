package com.example.podly.ui.home

import android.content.ComponentName
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.podly.playback.PlaybackService
import com.google.common.util.concurrent.MoreExecutors

@Composable
fun rememberPlayer(
): Player? {
    val context = LocalContext.current
    var player by remember { mutableStateOf(null as MediaController?) }


    DisposableEffect(context) {
        val sessionToken = SessionToken(
            context,
            ComponentName(context, PlaybackService::class.java)
        )
        val controllerFuture = MediaController.Builder(context, sessionToken)
            .buildAsync()
        controllerFuture.addListener(
            {
                player = controllerFuture.get()
            },
            MoreExecutors.directExecutor()
        )
        onDispose {
            MediaController.releaseFuture(controllerFuture)
        }
    }

    return player
}