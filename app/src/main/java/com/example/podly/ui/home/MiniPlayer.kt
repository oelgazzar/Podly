@file:kotlin.OptIn(ExperimentalMaterial3Api::class)

package com.example.podly.ui.home

import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.compose.material3.buttons.NextButton
import androidx.media3.ui.compose.material3.buttons.PlayPauseButton
import androidx.media3.ui.compose.material3.buttons.PreviousButton
import androidx.media3.ui.compose.material3.indicator.LinearProgressIndicator
import androidx.media3.ui.compose.state.rememberProgressStateWithTickInterval
import com.example.podly.R
import com.example.podly.ui.theme.AppTheme

@OptIn(UnstableApi::class, ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class
)
@Composable
fun MiniPlayer(
    title: String,
    player: Player?,
    modifier: Modifier = Modifier
) {
    val progressState = rememberProgressStateWithTickInterval(player, tickIntervalMs = 500)
    var progressBarWidth by remember { mutableFloatStateOf(0f) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Image(
            painterResource(R.drawable.track_cover_placeholder),
            null,
            Modifier
                .heightIn(max = 100.dp)
                .clip(MaterialTheme.shapes.medium)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                "Now playing",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            LinearProgressIndicator(
                player,
                modifier = Modifier
                    .onSizeChanged {
                        progressBarWidth = it.width.toFloat()
                    }
                    .pointerInput(true) {
                        detectDragGestures { change, _ ->
                            val fraction = change.position.x / progressBarWidth
                            player?.seekTo((progressState.durationMs * fraction).toLong())
                        }
                    }.pointerInput(true) {
                        detectTapGestures(
                            onTap = { pos ->
                                val fraction = pos.x / progressBarWidth
                                player?.seekTo((progressState.durationMs * fraction).toLong())
                            }
                        )
                    }
            )
            Row() {
                PreviousButton(player)
                PlayPauseButton(player)
                NextButton(player)
            }

        }

    }
}

@Preview
@Composable
private fun MiniPlayerPrev() {
    AppTheme {
        MiniPlayer(
            "Sports - Rock",
            null,
            modifier = Modifier.fillMaxWidth().padding(16.dp))
    }
}