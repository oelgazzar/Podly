package com.example.podly.ui.podcast

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.podly.ui.theme.AppTheme
import com.example.test.link_2

//@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPodcastScreen(
    navigateBack: () -> Unit = {},
    navigateToPodcastDetails: (Long) -> Unit = {},
    viewModel: AddPodcastViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DisposableEffect(uiState) {
        if (uiState is AddPodcastUiState.Success) {
            navigateToPodcastDetails((uiState as AddPodcastUiState.Success).channelId)
        }

        onDispose {
            if (uiState is AddPodcastUiState.Success) {
                viewModel.reset()
            }
        }
    }

    Scaffold(
        topBar = {
            AddPodcastTopBar(navigateBack)
        }
    ) { innerPadding ->
        AddPodcastContent(
            uiState = uiState,
            onFetchClicked = viewModel::fetchPodcast,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun AddPodcastContent(
    uiState: AddPodcastUiState,
    onFetchClicked: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var url by remember { mutableStateOf("https://rss.buzzsprout.com/162414.rss") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier
            .padding(top = 64.dp)
    ) {
        AnimatedVisibility(uiState !is AddPodcastUiState.Loading) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
            ) {
                Icon(
                    link_2,
                    null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            MaterialTheme.shapes.medium
                        )
                        .padding(8.dp)
                        .size(64.dp)
                )
                Text(
                    "Add a podcast by RSS feed",
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    "Enter the feed URL to fetch the podcast\ndetails and episodes.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }

        TextField(
            url,
            onValueChange = { url = it },
            label = {
                Text("RSS Feed URL")
            },
            leadingIcon = {
                Icon(link_2, null)
            },
            placeholder = {
                Text("https://example.com/feed.xml")
            },
            isError = uiState is AddPodcastUiState.Error,
            singleLine = true,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
        )

        Button(
            onClick = { onFetchClicked(url) },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                "Fetch Podcast",
            )
        }

        if (uiState is AddPodcastUiState.Error) {
            ErrorBox(
                title = uiState.message?:"Unknown error",
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (uiState is AddPodcastUiState.Loading) {
            LoadingBox()
        }


        if (uiState !is AddPodcastUiState.Loading) {
            Text(
                "you can enter any valid RSS feed URL\n(e.g. Apple podcasts, Podcast Index, etc.)",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun LoadingBox(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        CircularProgressIndicator()
        Text(
            "Fetching podcast information..."
        )
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun AddPodcastContentPrev() {
    AppTheme() {
        Surface {
            AddPodcastContent(
                AddPodcastUiState.Loading,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
    }
}

