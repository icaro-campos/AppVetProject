package br.unavet.appvetproject.domain.repository

import br.unavet.appvetproject.common.Difficulty
import br.unavet.appvetproject.domain.model.Question

interface GameRepository {
    suspend fun getQuestionsByDifficulty(difficulty: Difficulty): List<Question>
    suspend fun preloadQuestions()
}