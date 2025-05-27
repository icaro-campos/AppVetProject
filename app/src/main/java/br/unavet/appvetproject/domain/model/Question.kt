package br.unavet.appvetproject.domain.model

import br.unavet.appvetproject.common.Difficulty
import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val id: String,
    val question: String,
    val answer: String,
    val imageUrl: String? = null,
    val difficulty: Difficulty
)
