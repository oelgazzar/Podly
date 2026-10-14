package com.example.podly.ui.podcast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.example.podly.data.sample.sampleChannel
import com.example.podly.models.PodcastChannel
import com.example.podly.ui.theme.AppTheme
import com.example.test.arrow_back

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PodcastDetailsScreen(
    player: Player?,
    navigateBack: () -> Unit = {},
    viewModel: PodcastDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(arrow_back, null)
                    }
                },
                 colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { innerPadding ->
        Column {
            when {
                uiState is PodcastDetailsUiState.Success -> {
                    Spacer(
                        modifier = Modifier
                            .background(CardDefaults.cardColors().containerColor)
                            .fillMaxWidth()
                            .height(innerPadding.calculateTopPadding())
                    )
                    PodcastDetailsContent(
                        player = player,
                        channel = (uiState as PodcastDetailsUiState.Success).channel,
                        modifier = Modifier.padding(innerPadding.calculateBottomPadding())
                    )
                }

                else -> {
                    Text("Loading")
                }

            }
        }
    }
}

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun PodcastDetailsContent(
    player: Player?,
    channel: PodcastChannel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        ChannelDetailsCard(channel)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Episodes",
            modifier = Modifier.padding(start = 16.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(start = 16.dp)
                .padding(vertical = 8.dp)

        ) {
            items(channel.episodes, key = { it.id }) {
                EpisodeCard(
                    player = player,
                    episode = it,
                    artworkUrl = channel.artworkUrl,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun PodcastDetailsContentPrev() {
    AppTheme {
        PodcastDetailsContent(
            player = null,
            sampleChannel)
    }
}

