package br.unavet.appvetproject.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserStats(
    val totalPoints: Int,
    val totalGamesPlayed: Int,
    val correctAnswers: Int,
    val wrongAnswers: Int
)