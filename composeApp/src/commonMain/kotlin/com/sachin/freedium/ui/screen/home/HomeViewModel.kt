package com.sachin.freedium.ui.screen.home

import androidx.lifecycle.ViewModel
import com.sachin.freedium.SharedTextHandler
import com.sachin.freedium.util.validateUrl
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<HomeUiEvent>(capacity = Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onUrlChange(url: String) {
        _uiState.update { it.copy(url = url) }
    }

    fun submitUrl() {
        try {
            val url = validateUrl(url = uiState.value.url)
            _uiEvent.trySend(HomeUiEvent.NavigateToArticle(url = url))
        } catch (e: NullPointerException) {
            _uiEvent.trySend(HomeUiEvent.ShowSnackbar("Please enter a medium post link"))
        } catch (e: IllegalArgumentException) {
            _uiEvent.trySend(HomeUiEvent.ShowSnackbar("Invalid medium post link"))
        }
    }

}

data class HomeUiState(
    val url: String = "",
    //val error: String? = null
)

sealed interface HomeUiEvent {
    data class ShowSnackbar(val message: String) : HomeUiEvent
    data class NavigateToArticle(val url: String) : HomeUiEvent
}