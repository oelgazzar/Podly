package com.example.podly.ui.utils

import androidx.media3.datasource.HttpDataSource

fun Throwable.toUserMessage(): String = when (this) {
    is HttpDataSource.InvalidResponseCodeException -> {
        when (responseCode) {
            403, 404 -> "This track is unavailable"
            in 500..599 -> "Server error. Try again later"
            else -> "Unable to load this track"
        }
    }

    is HttpDataSource.InvalidContentTypeException ->
        "Invalid content type"

    is HttpDataSource.CleartextNotPermittedException ->
        "This audio source requires a secure connection"

    is HttpDataSource.HttpDataSourceException ->
        "Unable to load audio. Check your connection and try again"

    else -> "Something went wrong"
}