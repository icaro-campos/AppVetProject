package br.unavet.appvetproject.ui.game

sealed class GameUiEvent {
    data class OnAnswerChanged(val value: String) : GameUiEvent()
    data object OnSubmitAnswer : GameUiEvent()
    data object OnNextQuestion : GameUiEvent()
    data object OnFinishGame : GameUiEvent()
}