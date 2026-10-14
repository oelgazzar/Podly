package com.example.podly.data

import androidx.room3.withWriteTransaction
import com.example.podly.data.local.AppDatabase
import com.example.podly.data.local.ChannelDao
import com.example.podly.data.local.EpisodeDao
import com.example.podly.data.local.toDomain
import com.example.podly.data.local.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PodcastRepository @Inject constructor(
    private val db: AppDatabase,
    private val channelDao: ChannelDao,
    private val episodeDao: EpisodeDao
) {
    val client = OkHttpClient()

    suspend fun fetchFeed(url: String): Long {
        val request = Request.Builder()
            .header("User-Agent", "Podly/1.0")
            .url(url)
            .build()

        val newChannel = withContext(Dispatchers.IO) {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw IOException("HTTP error: ${response.code}")
                }

                FeedParser.parse(response.body.string())
            }
        }.copy(feedUrl = url)

        val channelId = db.withWriteTransaction {
            channelDao.insertChannel(newChannel.toEntity())
            val savedChannel = channelDao.getByFeedUrl(url).toDomain()
            val channelId = savedChannel.id
            episodeDao.insertEpisodes(newChannel.episodes.map { it.copy(channelId = channelId) }.toEntity())
            channelId
        }

        return channelId
    }

    fun getChannelWithEpisodes(channelId: Long) = channelDao.getChannelWithEpisodes(channelId).map {
        it.toDomain()
    }

    fun getAllChannels() = channelDao.getAll().map { it.toDomain() }

    fun getEpisode(episodeId: Long) = episodeDao.getEpisode(episodeId).map { it.toDomain() }
}