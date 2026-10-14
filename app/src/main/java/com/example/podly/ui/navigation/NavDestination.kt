package com.example.podly.ui.navigation

import kotlinx.serialization.Serializable

sealed interface NavDestination {
    @Serializable
    object Home: NavDestination
    @Serializable
    object AddPodcast: NavDestination
    @Serializable
    data class PodcastDetails(val channelId: Long): NavDestination
    @Serializable
    data class NowPlaying(val episodeId: Long): NavDestination
}