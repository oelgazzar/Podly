package com.example.podly.playback

import androidx.lifecycle.ViewModel
import com.example.podly.data.PodcastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import com.example.podly.models.Episode

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val playback: PlaybackController,
    private val podcastRepository: PodcastRepository
): ViewModel() {
    val uiState = playback.state

    @OptIn(ExperimentalCoroutinesApi::class)
    val currentEpisode = playback.state
        .map { it.episodeId }
        .distinctUntilChanged()
        .flatMapLatest { id ->
            if (id == null) flowOf(null) else podcastRepository.getEpisode(id)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun onPlayPause(episode: Episode?) {
        if (episode == null) return

        if (episode == currentEpisode.value) {
            playback.togglePlayPause()
        } else {
            playback.play(episode)
        }
    }

    fun onSeek(ms: Long) = playback.seekTo(ms)
}