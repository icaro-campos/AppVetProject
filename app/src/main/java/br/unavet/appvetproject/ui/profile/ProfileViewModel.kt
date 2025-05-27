package br.unavet.appvetproject.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.unavet.appvetproject.domain.repository.UserStatsRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ProfileViewModel(
    private val userStatsRepository: UserStatsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val _uiEffect = Channel<ProfileUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    init {
        loadUserStats()
    }

    private fun loadUserStats() {
        viewModelScope.launch {
            val stats = userStatsRepository.getUserStats()
            _uiState.update {
                it.copy(
                    totalPoints = stats.totalPoints,
                    totalGamesPlayed = stats.totalGamesPlayed
                )
            }
        }
    }

    fun onEvent(event: ProfileUiEvent) {
        when (event) {
            is ProfileUiEvent.OnBackClicked -> {
                sendEffect(ProfileUiEffect.NavigateBack)
            }
        }
    }

    private fun sendEffect(effect: ProfileUiEffect) {
        viewModelScope.launch {
            _uiEffect.send(effect)
        }
    }
}