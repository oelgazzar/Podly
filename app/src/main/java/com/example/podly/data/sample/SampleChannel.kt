package com.example.podly.data.sample

import com.example.podly.models.Episode
import com.example.podly.models.PodcastChannel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val sampleChannel = PodcastChannel(
    title = "The Daily",
    author = "John Doe",
    category = "Misc.",
    description = "This is what the news should sound like. " +
            "The biggest stories of our time, told by the best journalists in the world.",
    artworkUrl = "https://example.com/podcast-artwork.jpg",
    episodes = listOf(
        Episode(
            title = "The Future of Artificial Intelligence",
            description = "A look at how AI is changing our lives.",
            audioUrl = "https://example.com/episode1.mp3",
            pubDate = LocalDateTime.parse("Mon, 12 Oct 2026 08:00:00 GMT", DateTimeFormatter.RFC_1123_DATE_TIME),
            duration = 154
        ),
        Episode(
            title = "Inside the Gaming Industry",
            description = "How video games became a global phenomenon.",
            audioUrl = "https://example.com/episode2.mp3",
            pubDate = LocalDateTime.parse("Sun, 11 Oct 2026 08:00:00 GMT", DateTimeFormatter.RFC_1123_DATE_TIME),
            duration = 132
        ),
        Episode(
            title = "The Science of Sleep",
            description = "What happens to your brain while you sleep?",
            audioUrl = "https://example.com/episode3.mp3",
            pubDate = LocalDateTime.parse("Sat, 10 Oct 2026 08:00:00 GMT", DateTimeFormatter.RFC_1123_DATE_TIME),
            duration = 97
        )
    )
)