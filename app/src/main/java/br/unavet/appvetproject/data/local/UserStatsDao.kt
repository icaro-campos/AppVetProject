package br.unavet.appvetproject.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.unavet.appvetproject.data.local.model_entity.UserStatsEntity

@Dao
interface UserStatsDao {

    @Query("SELECT * FROM user_stats_table WHERE id = :id")
    suspend fun getUserStats(id: String = "default"): UserStatsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserStats(stats: UserStatsEntity)

    @Query("DELETE FROM user_stats_table")
    suspend fun clearUserStats()
}