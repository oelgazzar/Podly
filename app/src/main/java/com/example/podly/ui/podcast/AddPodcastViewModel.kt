package com.example.podly.ui.podcast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.podly.data.PodcastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface AddPodcastUiState {
    data class Success(val channelId: Long) : AddPodcastUiState
    data class Error(val message: String?) : AddPodcastUiState
    object Loading : AddPodcastUiState
    object Idle : AddPodcastUiState
}

@HiltViewModel
class AddPodcastViewModel @Inject constructor(
    private val podcastRepository: PodcastRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<AddPodcastUiState>(AddPodcastUiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun fetchPodcast(url: String) {
        viewModelScope.launch {
            _uiState.value = AddPodcastUiState.Loading
            try {
                delay(2000)
                val channelId = podcastRepository.fetchFeed(url)
                _uiState.value = AddPodcastUiState.Success(channelId)
            } catch (e: Exception) {
                _uiState.value = AddPodcastUiState.Error(e.toString())
            }
        }
    }

    fun reset() {
        _uiState.value = AddPodcastUiState.Idle
    }
}