package com.example.podly.data.local

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import androidx.room3.PrimaryKey
import com.example.podly.models.Episode
import com.example.podly.utils.parsePubDate

@Entity(
    tableName = "episode",
    indices = [
        Index(value = ["guid", "channel_id"], unique = true),
        Index(value = ["channel_id"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = ChannelEntity::class,
            parentColumns = ["id"],
            childColumns = ["channel_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class EpisodeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String?,
    val description: String?,
    @ColumnInfo(name = "pub_date")
    val pubDate: String?,
    val duration: Long?,
    val guid: String? = null,
    @ColumnInfo(name = "audio_url")
    val audioUrl: String?,
    @ColumnInfo(name = "channel_id")
    val channelId: Long
)

fun EpisodeEntity.toDomain() = Episode(
    id = id,
    title = title,
    description = description,
    pubDate = pubDate?.let { parsePubDate(pubDate) },
    duration = duration,
    guid = guid,
    audioUrl = audioUrl
)

fun List<EpisodeEntity>.toDomain() = map { it.toDomain() }

fun Episode.toEntity() = EpisodeEntity(
    id = id,
    title = title,
    description = description,
    pubDate = pubDate?.toString(),
    duration = duration,
    guid = guid,
    audioUrl = audioUrl,
    channelId = channelId
)

fun List<Episode>.toEntity() = map { it.toEntity() }
