package br.unavet.appvetproject.ui.game

import br.unavet.appvetproject.domain.model.Question

data class GameUiState(
    val currentQuestion: Question? = null,
    val userInput: String = "",
    val score: Int = 0,
    val attempts: Int = 0,
    val isGameOver: Boolean = false,
    val isAnswerCorrect: Boolean? = null
)
