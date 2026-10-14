package com.example.podly.ui.home

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.UnstableApi
import com.example.podly.ui.theme.AppTheme

@Composable
fun HomeScreen(
    navigateToPodcastDetails: (Long) -> Unit = {},
    navigateToAddPodcast: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            HomeTopBar(
                navigateToAddPodcast
            )
        }
    ) { innerPadding ->
        HomeContent(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        )

    }
}

@OptIn(UnstableApi::class)
@Composable
fun HomeContent(
    modifier: Modifier = Modifier
) {
}

@Preview
@Composable
private fun HomeContentPrev() {
    AppTheme {
        HomeContent(
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
private fun HomeScreenPrev() {
    AppTheme {
        HomeScreen()
    }
}