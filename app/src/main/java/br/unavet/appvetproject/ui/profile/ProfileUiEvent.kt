package br.unavet.appvetproject.ui.profile

sealed class ProfileUiEvent {
    data object OnBackClicked : ProfileUiEvent()
}