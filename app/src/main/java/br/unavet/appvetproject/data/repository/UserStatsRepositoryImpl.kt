package br.unavet.appvetproject.data.repository

import br.unavet.appvetproject.data.local.UserStatsDao
import br.unavet.appvetproject.data.local.model_entity.UserStatsEntity
import br.unavet.appvetproject.domain.model.UserStats
import br.unavet.appvetproject.domain.repository.UserStatsRepository

class UserStatsRepositoryImpl(
    private val userStatsDao: UserStatsDao
) : UserStatsRepository {

    override suspend fun getUserStats(): UserStats {
        return userStatsDao.getUserStats()?.toDomain() ?: UserStats(
            totalPoints = 0,
            totalGamesPlayed = 0,
            correctAnswers = 0,
            wrongAnswers = 0
        )
    }

    override suspend fun saveUserStats(stats: UserStats) {
        userStatsDao.saveUserStats(stats.toEntity())
    }

    override suspend fun clearStats() {
        userStatsDao.clearUserStats()
    }
}

private fun UserStatsEntity.toDomain() = UserStats(
    totalPoints = totalPoints,
    totalGamesPlayed = totalGamesPlayed,
    correctAnswers = correctAnswers,
    wrongAnswers = wrongAnswers
)

private fun UserStats.toEntity() = UserStatsEntity(
    totalPoints = totalPoints,
    totalGamesPlayed = totalGamesPlayed,
    correctAnswers = correctAnswers,
    wrongAnswers = wrongAnswers
)