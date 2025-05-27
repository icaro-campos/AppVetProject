package br.unavet.appvetproject.domain.repository

import br.unavet.appvetproject.domain.model.UserStats

interface UserStatsRepository {
    suspend fun getUserStats(): UserStats
    suspend fun saveUserStats(stats: UserStats)
    suspend fun clearStats()
}