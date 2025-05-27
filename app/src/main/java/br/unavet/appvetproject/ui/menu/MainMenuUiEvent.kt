package br.unavet.appvetproject.ui.menu

sealed class MainMenuUiEvent {
    data object OnPlayClicked : MainMenuUiEvent()
    data object OnProfileClicked : MainMenuUiEvent()
}