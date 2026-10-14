package com.example.podly.di

import android.content.Context
import androidx.room3.Room
import com.example.podly.data.local.AppDatabase
import com.example.podly.data.local.ChannelDao
import com.example.podly.data.local.EpisodeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule{
    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "podly-database"
        ).build()
    }

    @Singleton
    @Provides
    fun providesChannelDao(database: AppDatabase): ChannelDao = database.channelDao()

    @Singleton
    @Provides
    fun providesEpisodeDao(database: AppDatabase): EpisodeDao = database.episodeDao()
}