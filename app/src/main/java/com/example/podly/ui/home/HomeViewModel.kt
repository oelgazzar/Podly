package com.example.podly.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.podly.data.PodcastRepository
import com.example.podly.models.PodcastChannel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface HomeUiState {
    data class Success(val channels: List<PodcastChannel>) : HomeUiState
    data class Error(val message: String?) : HomeUiState
    object Loading : HomeUiState
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val podcastRepository: PodcastRepository
): ViewModel() {
    val uiState = podcastRepository.getAllChannels()
        .map {
            HomeUiState.Success(it)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            HomeUiState.Loading
        )
}