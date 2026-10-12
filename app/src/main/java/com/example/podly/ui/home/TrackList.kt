package com.example.podly.ui.home

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.podly.data.sample.sampleTracks
import com.example.podly.models.Track
import com.example.podly.ui.theme.AppTheme
import com.example.test.podcasts

@Composable
fun TrackList(
    tracks: List<Track> = sampleTracks,
    currentTrackUri: Uri?,
    setCurrentTrack: (Track) -> Unit,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Card(modifier = modifier
        .wrapContentSize(align = Alignment.TopCenter)) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            tracks.forEachIndexed { index, track ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .clickable(
                            onClick = {setCurrentTrack(track)}
                        )
                ) {
                    Text(
                        track.title,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f)
                    )
                    if (currentTrackUri == track.uri) {
                        val anim = rememberInfiniteTransition()
                        val alpha by anim.animateFloat(0.3f, 1f, animationSpec = infiniteRepeatable(
                            tween(1000, easing = LinearEasing),
                            repeatMode = RepeatMode.Reverse
                        ))
                        Icon(podcasts, null, modifier = Modifier
                            .alpha(alpha).padding(end = 32.dp))
                    }
                }
                if (index < tracks.lastIndex) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = .25f))
                }
            }

        }
    }
}

@Preview
@Composable
private fun TrackListPrev() {
    AppTheme {
        TrackList(sampleTracks, sampleTracks.first().uri, {})
    }
}