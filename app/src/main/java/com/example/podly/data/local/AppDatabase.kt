package com.example.podly.data.local

import androidx.room3.Database
import androidx.room3.RoomDatabase

@Database(entities = [ChannelEntity::class, EpisodeEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun channelDao(): ChannelDao
    abstract fun episodeDao(): EpisodeDao
}