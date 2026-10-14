package com.example.podly.data.local

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey
import com.example.podly.models.Episode
import com.example.podly.models.PodcastChannel

@Entity(tableName = "channel", indices = [Index(value = ["feed_url"], unique = true)])
data class ChannelEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String? = null,
    val author: String? = null,
    val category: String? = null,
    @ColumnInfo(name = "artwork_url")
    val artworkUrl: String? = null,
    @ColumnInfo(name = "feed_url")
    val feedUrl: String,
)

fun ChannelEntity.toDomain(episodes: List<Episode> = emptyList()): PodcastChannel = PodcastChannel(
    id = id,
    title = title,
    description = description,
    author = author,
    category = category,
    artworkUrl = artworkUrl,
    feedUrl = feedUrl,
    episodes = episodes
)

fun List<ChannelEntity>.toDomain() = map { it.toDomain() }

fun Map<ChannelEntity, List<EpisodeEntity>>.toDomain() = this.keys.first().toDomain(this.values.first().toDomain())

fun PodcastChannel.toEntity() = ChannelEntity(
    id = id,
    title = title ?: "",
    description = description,
    author = author,
    category = category,
    artworkUrl = artworkUrl,
    feedUrl = feedUrl,
)

fun List<PodcastChannel>.toEntity() = map { it.toEntity() }
