package com.example.podly.models

import androidx.core.net.toUri

data class Track(
    val title: String,
    val url: String
) {
    val uri
        get() = url.toUri()
}
