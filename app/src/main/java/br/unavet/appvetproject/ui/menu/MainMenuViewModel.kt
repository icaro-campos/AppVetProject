package br.unavet.appvetproject.ui.menu

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

class MainMenuViewModel(
    private val userStatsRepository: UserStatsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainMenuUiState())
    val uiState: StateFlow<MainMenuUiState> = _uiState.asStateFlow()

    private val _uiEffect = Channel<MainMenuUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    init {
        loadUserStats()
    }

    private fun loadUserStats() {
        viewModelScope.launch {
            val stats = userStatsRepository.getUserStats()
            _uiState.update {
                it.copy(
                    userName = "Jogador",
                    totalPoints = stats.totalPoints,
                    totalGamesPlayed = stats.totalGamesPlayed
                )
            }
        }
    }

    fun onEvent(event: MainMenuUiEvent) {
        when (event) {
            is MainMenuUiEvent.OnPlayClicked -> {
                sendEffect(MainMenuUiEffect.NavigateToGame)
            }

            is MainMenuUiEvent.OnProfileClicked -> {
                sendEffect(MainMenuUiEffect.NavigateToProfile)
            }
        }
    }

    private fun sendEffect(effect: MainMenuUiEffect) {
        viewModelScope.launch {
            _uiEffect.send(effect)
        }
    }
}