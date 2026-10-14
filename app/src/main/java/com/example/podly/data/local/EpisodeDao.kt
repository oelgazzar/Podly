package com.example.podly.data.local

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy

@Dao
interface EpisodeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEpisode(episode: EpisodeEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEpisodes(episodes: List<EpisodeEntity>)
}