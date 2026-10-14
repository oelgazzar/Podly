package com.example.podly.ui.home

import androidx.annotation.OptIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import coil3.compose.AsyncImage
import com.example.podly.data.sample.sampleChannel
import com.example.podly.models.PodcastChannel
import com.example.podly.ui.theme.AppTheme
import com.example.test.podcasts

@Composable
fun HomeScreen(
    navigateToPodcastDetails: (Long) -> Unit = {},
    navigateToAddPodcast: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            HomeTopBar(
                navigateToAddPodcast
            )
        }) { innerPadding ->
        if (uiState is HomeUiState.Success) {
            HomeContent(
                (uiState as HomeUiState.Success).channels,
                onChannelSelected = navigateToPodcastDetails,
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
            )
        }

    }
}

@OptIn(UnstableApi::class)
@Composable
fun HomeContent(
    channels: List<PodcastChannel>,
    onChannelSelected: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            "Subscriptions", style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(channels, key = { it.id }) {
                Card(
                    modifier = Modifier.height(IntrinsicSize.Max)
                        .clickable(
                            onClick =  { onChannelSelected(it.id) }
                        )
                ) {
                    Column(
                    ) {
                        AsyncImage(
                            model = it.artworkUrl,
                            null,
                            placeholder = rememberVectorPainter(podcasts),
                            modifier = Modifier.size(128.dp)
                        )
                        Text(
                            it.title ?: "",
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .width(128.dp)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeContentPrev() {
    AppTheme {
        HomeContent(
            listOf(sampleChannel, sampleChannel, sampleChannel),
            {},
            modifier = Modifier.fillMaxSize()
        )
    }
}