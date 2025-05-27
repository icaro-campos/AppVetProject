package br.unavet.appvetproject.data.repository

import br.unavet.appvetproject.common.Difficulty
import br.unavet.appvetproject.data.local.QuestionDao
import br.unavet.appvetproject.data.local.model_entity.QuestionEntity
import br.unavet.appvetproject.domain.model.Question
import br.unavet.appvetproject.domain.repository.GameRepository

class GameRepositoryImpl(
    private val questionDao: QuestionDao
) : GameRepository {
    override suspend fun getQuestionsByDifficulty(difficulty: Difficulty): List<Question> {
        return questionDao.getQuestionsByDifficulty(difficulty).map { it.toDomain() }
    }

    override suspend fun preloadQuestions() {
        val sampleQuestions = listOf(
            QuestionEntity(
                id = "1",
                question = "Animal doméstico",
                answer = "CACHORRO",
                imageUrl = null,
                difficulty = Difficulty.EASY
            ),
            QuestionEntity(
                id = "2",
                question = "Animal que mia",
                answer = "GATO",
                imageUrl = null,
                difficulty = Difficulty.EASY
            ),
            QuestionEntity(
                id = "3",
                question = "Profissão que cuida dos pets",
                answer = "VETERINARIO",
                imageUrl = null,
                difficulty = Difficulty.MEDIUM
            ),
            QuestionEntity(
                id = "4",
                question = "Equipamento médico usado em consultas",
                answer = "ESTETO",
                imageUrl = null,
                difficulty = Difficulty.MEDIUM
            ),
            QuestionEntity(
                id = "5",
                question = "Mede a saturação",
                answer = "OXIMETRO",
                imageUrl = null,
                difficulty = Difficulty.HARD
            ),
            QuestionEntity(
                id = "6",
                question = "Escutar os sons do corpo",
                answer = "AUSCULTA",
                imageUrl = null,
                difficulty = Difficulty.HARD
            )
        )
        questionDao.clearQuestions()
        questionDao.insertAll(sampleQuestions)
    }
}

private fun QuestionEntity.toDomain() = Question(
    id = id,
    question = question,
    answer = answer,
    imageUrl = imageUrl,
    difficulty = difficulty
)