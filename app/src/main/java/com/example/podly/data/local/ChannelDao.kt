package com.example.podly.data.local

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.example.podly.models.Episode
import kotlinx.coroutines.flow.Flow

@Dao
interface ChannelDao {
    @Query("SELECT * FROM channel")
    fun getAll(): Flow<List<ChannelEntity>>

    @Query("SELECT * FROM channel WHERE id = :id")
    fun getById(id: Long): Flow<ChannelEntity>

    @Query("SELECT * FROM channel JOIN episode ON channel.id = episode.channel_id WHERE channel.id = :id")
    fun getChannelWithEpisodes(id: Long): Flow<Map<ChannelEntity, List<EpisodeEntity>>>

    @Query("SELECT * FROM channel WHERE feed_url = :feedUrl")
    fun getByFeedUrl(feedUrl: String): ChannelEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertChannel(channel: ChannelEntity)
}