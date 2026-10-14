@file:kotlin.OptIn(ExperimentalMaterial3Api::class)

package com.example.podly.ui.home

import androidx.annotation.OptIn
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import com.example.podly.data.sample.sampleChannel
import com.example.podly.models.Episode
import com.example.podly.playback.PlayerUiState
import com.example.podly.ui.theme.AppTheme
import com.example.test.pause
import com.example.test.play_arrow
import com.example.test.podcasts
import com.example.test.skip_next
import com.example.test.skip_previous

@OptIn(
    UnstableApi::class, ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class
)
@Composable
fun MiniPlayer(
    playerState: PlayerUiState,
    currentEpisode: Episode?,
    playPause: () -> Unit,
    seekTo: (Long) -> Unit,
    skipNext: () -> Unit,
    skipPrevious: () -> Unit,
    onExpanded: () -> Unit,
    modifier: Modifier = Modifier
) {
    var progressBarWidth by remember { mutableFloatStateOf(0f) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            podcasts,
            null,
            Modifier
                .size(64.dp)
                .heightIn(max = 100.dp)
                .clip(MaterialTheme.shapes.medium)
                .pointerInput(true) {
                    detectTapGestures(
                        onTap = {
                            onExpanded()
                        }
                    )
                }
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = currentEpisode?.title ?: "",
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
                progress = {
                    if (playerState.duration == 0L) 0f
                    else (playerState.currentPosition.toFloat() / playerState.duration).coerceAtLeast(
                        0f
                    )
                },
                drawStopIndicator = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .onSizeChanged {
                        progressBarWidth = it.width.toFloat()
                    }
                    .pointerInput(true) {
                        detectDragGestures { change, _ ->
                            val fraction = change.position.x / progressBarWidth
                            seekTo((playerState.duration * fraction).toLong())
                        }
                    }
                    .pointerInput(true) {
                        detectTapGestures(
                            onTap = { pos ->
                                val fraction = pos.x / progressBarWidth
                                seekTo((playerState.duration * fraction).toLong())
                            }
                        )
                    }
            )

            Row {
                IconButton(
                    onClick = skipPrevious,
                    enabled = playerState.hasPreviousItem
                ) {
                    Icon(
                        skip_previous,
                        null
                    )
                }
                IconButton(
                    onClick = playPause,
                    enabled = currentEpisode != null && !playerState.isBuffering
                ) {
                    Icon(
                        if (playerState.isPlaying) pause else play_arrow,
                        null
                    )
                }
                IconButton(
                    onClick = skipNext,
                    enabled = playerState.hasNextItem
                ) {
                    Icon(
                        skip_next,
                        null
                    )
                }
            }

        }

    }
}

@Preview
@Composable
private fun MiniPlayerPrev() {
    AppTheme {
        MiniPlayer(
            PlayerUiState(isPlaying = true, currentPosition = 0, duration = -9),
            sampleChannel.episodes.first(),
            {},
            {},
            {},
            {},
            {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}