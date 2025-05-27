package br.unavet.appvetproject.data.local.model_entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "user_stats_table")
@Serializable
data class UserStatsEntity(
    @PrimaryKey val id: String = "default",
    val totalPoints: Int,
    val totalGamesPlayed: Int,
    val correctAnswers: Int,
    val wrongAnswers: Int
)