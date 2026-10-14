package com.example.podly.models

data class PodcastChannel(
    val id: Long = 0,
    val title: String?,
    val description: String? = null,
    val author: String? = null,
    val artworkUrl: String? = null,
    val category: String? = null,
    val feedUrl: String = "",
    val episodes: List<Episode>
)
