package br.unavet.appvetproject.ui.menu

sealed class MainMenuUiEffect {
    data object NavigateToGame : MainMenuUiEffect()
    data object NavigateToProfile : MainMenuUiEffect()
}