package com.example.podly.models

import java.time.Duration
import java.time.LocalDateTime

data class Episode(
    val id: Long = 0,
    val title: String?,
    val description: String?,
    val pubDate: LocalDateTime?,
    val duration: Long?,
    val guid: String? = null,
    val audioUrl: String?,
    val channelId: Long = 0
)
