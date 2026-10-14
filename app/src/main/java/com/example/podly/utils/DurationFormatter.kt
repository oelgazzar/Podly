package com.example.podly.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun parseDuration(value: String?): Long? {
    value?:return null

    return value.split(":")
        .map { it.toLong() }
        .reversed()
        .mapIndexed { index, number ->
            number * when (index) {
                0 -> 1L
                1 -> 60L
                2 -> 3600L
                else -> throw IllegalArgumentException("Invalid duration")
            }
        }
        .sum()
}

fun Long.toDurationString(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60

    return if (hours > 0) {
        "%d:%02d:%02d".format(hours, minutes, seconds)
    } else {
        "%d:%02d".format(minutes, seconds)
    }
}

fun parsePubDate(text: String?): LocalDateTime? {
    text?:return null

    val formats = listOf(
        DateTimeFormatter.ISO_DATE_TIME,
        DateTimeFormatter.RFC_1123_DATE_TIME)

    return formats.firstNotNullOfOrNull {
        it.runCatching {
            LocalDateTime.parse(text, it)
        }.getOrNull()
    }
}