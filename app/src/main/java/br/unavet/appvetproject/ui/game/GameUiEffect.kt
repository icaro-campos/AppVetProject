package br.unavet.appvetproject.ui.game

sealed class GameUiEffect {
    data object NavigateToMainMenu : GameUiEffect()
    data class ShowMessage(val message: String) : GameUiEffect()
}