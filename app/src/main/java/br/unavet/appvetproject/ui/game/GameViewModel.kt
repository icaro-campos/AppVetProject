package br.unavet.appvetproject.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.unavet.appvetproject.common.Difficulty
import br.unavet.appvetproject.domain.model.Question
import br.unavet.appvetproject.domain.repository.GameRepository
import br.unavet.appvetproject.domain.repository.UserStatsRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel(
    private val gameRepository: GameRepository,
    private val userStatsRepository: UserStatsRepository,
    private val difficulty: Difficulty
) : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private val _uiEffect = Channel<GameUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    private var questions: List<Question> = emptyList()
    private var currentIndex = 0

    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            questions = gameRepository.getQuestionsByDifficulty(difficulty).shuffled()

            if (questions.isNotEmpty()) {
                loadNextQuestion()
            } else {
                _uiEffect.send(GameUiEffect.ShowMessage("Nenhuma pergunta disponível para este nível"))
                _uiEffect.send(GameUiEffect.NavigateToMainMenu)
            }
        }
    }

    private fun loadNextQuestion() {
        if (currentIndex < questions.size) {
            _uiState.update {
                it.copy(
                    currentQuestion = questions[currentIndex],
                    userInput = "",
                    isAnswerCorrect = null
                )
            }
        } else {
            endGame()
        }
    }

    fun onEvent(event: GameUiEvent) {
        when (event) {
            is GameUiEvent.OnAnswerChanged -> {
                _uiState.update { it.copy(userInput = event.value) }
            }

            is GameUiEvent.OnSubmitAnswer -> {
                checkAnswer()
            }

            is GameUiEvent.OnNextQuestion -> {
                currentIndex++
                loadNextQuestion()
            }

            is GameUiEvent.OnFinishGame -> {
                endGame()
            }
        }
    }

    private fun checkAnswer() {
        val state = _uiState.value
        val correctAnswer = state.currentQuestion?.answer ?: return
        val isCorrect = state.userInput.equals(correctAnswer, ignoreCase = true)

        if (isCorrect) {
            _uiState.update {
                it.copy(
                    score = it.score + 10,
                    isAnswerCorrect = true
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    attempts = it.attempts + 1,
                    isAnswerCorrect = false
                )
            }
        }
    }

    private fun endGame() {
        _uiState.update { it.copy(isGameOver = true) }

        viewModelScope.launch {
            val currentStats = userStatsRepository.getUserStats()

            val updatedStats = currentStats.copy(
                totalPoints = currentStats.totalPoints + _uiState.value.score,
                totalGamesPlayed = currentStats.totalGamesPlayed + 1,
                correctAnswers = currentStats.correctAnswers + calculateCorrectAnswers(),
                wrongAnswers = currentStats.wrongAnswers + calculateWrongAnswers()
            )

            userStatsRepository.saveUserStats(updatedStats)
            _uiEffect.send(GameUiEffect.NavigateToMainMenu)
        }
    }

    private fun calculateCorrectAnswers(): Int {
        return _uiState.value.score / 10
    }

    private fun calculateWrongAnswers(): Int {
        return _uiState.value.attempts
    }
}