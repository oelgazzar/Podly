package com.example.podly.playback

data class PlayerUiState(
    val episodeId: Long? = null,
    val isPlaying: Boolean = false,
    val isBuffering: Boolean = false,
    val currentPosition: Long = 0L,
    val duration: Long = 0L,
    val hasPreviousItem: Boolean = false,
    val hasNextItem: Boolean = false
)
