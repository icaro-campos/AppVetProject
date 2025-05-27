package br.unavet.appvetproject.ui.profile

sealed class ProfileUiEffect {
    data object NavigateBack : ProfileUiEffect()
}