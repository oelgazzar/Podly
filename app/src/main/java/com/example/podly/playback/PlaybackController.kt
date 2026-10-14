package com.example.podly.playback

import android.content.ComponentName
import android.content.Context
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.podly.models.Episode
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import kotlin.time.Duration.Companion.milliseconds

@Singleton
class PlaybackController @Inject constructor(
    @ApplicationContext private val context: Context,
    private val scope: CoroutineScope
) {
    private val _state = MutableStateFlow(PlayerUiState())
    val state = _state.asStateFlow()

    private var controller: MediaController? = null
    private var tickerJob: Job? = null

    private val listener = object : Player.Listener {
        override fun onEvents(player: Player, events: Player.Events) {
            updateState(player)
            if (player.isPlaying) startTicker(player) else stopTicker()
        }
    }

    fun connect() {
        println("connecting")
        if (controller != null) return
        val token = SessionToken(context, ComponentName(context, PlaybackService::class.java))
        val future = MediaController.Builder(context, token).buildAsync()
        future.addListener(
            {
                controller = future.get().also {
                    it.addListener(listener)
                    updateState(it)
                }
                println("controller up and running $controller")
            },
            MoreExecutors.directExecutor()
        )
    }

    fun play(episode: Episode) {
        val c = controller ?: return
        println("should play")

        val item = MediaItem.Builder()
            .setMediaId(episode.id.toString())
            .setUri(episode.audioUrl?.toUri())
            .build()
        c.setMediaItem(item)
        c.prepare()
        c.play()
    }

    fun togglePlayPause() {
        controller?.let {
            if (_state.value.isPlaying) it.pause() else it.play()
        }
    }

    fun seekTo(position: Long) {
        controller?.seekTo(position)
    }

    private fun updateState(player: Player) {
        _state.update {
            PlayerUiState(
                episodeId = player.currentMediaItem?.mediaId?.toLongOrNull(),
                isPlaying = player.isPlaying,
                isBuffering = player.playbackState == Player.STATE_BUFFERING,
                currentPosition = player.currentPosition,
                duration = player.duration.coerceAtLeast(0),
                hasPreviousItem = player.hasPreviousMediaItem(),
                hasNextItem = player.hasNextMediaItem()
            )
        }
    }

    private fun startTicker(player: Player) {
        if (tickerJob?.isActive == true) return

        tickerJob = scope.launch() {
            while (true) {
                delay(1000.milliseconds)
                withContext(Dispatchers.Main) {
                    updateState(player)
                }
            }
        }
    }

    private fun stopTicker() {
        tickerJob?.cancel()
        tickerJob = null
    }

    fun release() {
        controller?.release()
        controller = null
    }
}