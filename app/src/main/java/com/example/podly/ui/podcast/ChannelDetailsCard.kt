package com.example.podly.ui.podcast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.ImageRequest
import com.example.podly.data.sample.sampleChannel
import com.example.podly.models.PodcastChannel
import com.example.podly.ui.theme.AppTheme
import com.example.test.play_arrow

@Composable
fun ChannelDetailsCard(
    channel: PodcastChannel,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Card(
        shape = RectangleShape,
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(channel.artworkUrl)
                        .httpHeaders(
                            NetworkHeaders.Builder()
                                .add("User-Agent", ("Mozilla/5.0 (Windows NT 10.0; Win64; x64)"))
                                .build()
                        )
                        .build(),
                    null,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(128.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
                Column(
                    verticalArrangement =
                        Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                    modifier = Modifier.Companion
                ) {
                    Text(
                        channel.title ?: "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        channel.author ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    channel.category?.let {
                        Text(
                            channel.category,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.surfaceDim, CircleShape)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
            Text(
                channel.description ?: "",
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text("Subscribe")
                }

                OutlinedButton(
                    onClick = {},
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(play_arrow, null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Play Latest")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ChannelDetailsCardPrev() {
    AppTheme {
        ChannelDetailsCard(sampleChannel)
    }
}