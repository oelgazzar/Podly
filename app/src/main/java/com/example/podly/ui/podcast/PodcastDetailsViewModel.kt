package com.example.podly.ui.podcast

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.podly.data.PodcastRepository
import com.example.podly.models.PodcastChannel
import com.example.podly.ui.navigation.NavDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface PodcastDetailsUiState {
    object Loading : PodcastDetailsUiState
    data class Error(val message: String?) : PodcastDetailsUiState
    data class Success(val channel: PodcastChannel) : PodcastDetailsUiState
}

@HiltViewModel
class PodcastDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    podcastRepository: PodcastRepository
): ViewModel() {
    private val channelId: Long = checkNotNull(savedStateHandle.toRoute<NavDestination.PodcastDetails>().channelId)
    val uiState: StateFlow<PodcastDetailsUiState> = podcastRepository.getChannelWithEpisodes(channelId)
        .map { PodcastDetailsUiState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PodcastDetailsUiState.Loading
        )
}