package com.example.podly.ui.podcast

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.Player
import androidx.media3.common.listen
import androidx.media3.common.util.UnstableApi
import com.example.podly.LocalPlayer
import com.example.podly.data.sample.sampleChannel
import com.example.podly.models.Episode
import com.example.podly.ui.theme.AppTheme
import com.example.podly.utils.toDurationString
import com.example.test.more_vert
import com.example.test.pause_circle
import com.example.test.play_circle
import com.example.test.podcasts
import java.time.format.DateTimeFormatter

@UnstableApi
@Composable
fun EpisodeCard(
    episode: Episode,
    isCurrent: Boolean,
    isPlaying: Boolean,
    playPause: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            podcasts,
            null,
            modifier = Modifier
                .padding(end = 8.dp)
                .size(32.dp)
                .clip(MaterialTheme.shapes.medium)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                episode.title ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                buildAnnotatedString {
                    append(
                        episode.pubDate?.format(
                            DateTimeFormatter.ofPattern("MMM dd, yyyy")
                        )
                    )
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)) {
                        append(" . ")
                    }
                    append(episode.duration?.toDurationString())
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        IconButton(
            onClick = playPause
        ) {
            Icon(
                if (isPlaying && isCurrent) pause_circle else play_circle,
                null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(2.dp)
                    .size(36.dp)
            )
        }
        IconButton(
            onClick = {}
        ) {
            Icon(
                more_vert,
                null,
            )
        }
    }
}

@OptIn(UnstableApi::class)
@Preview(showSystemUi = true)
@Composable
private fun EpisodeCardPrev() {
    AppTheme {
        EpisodeCard(
            sampleChannel.episodes.first(),
            isCurrent = true,
            isPlaying = true,
            playPause = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}