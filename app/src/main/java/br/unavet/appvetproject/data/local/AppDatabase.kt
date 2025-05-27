package br.unavet.appvetproject.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.unavet.appvetproject.data.local.model_entity.QuestionEntity
import br.unavet.appvetproject.data.local.model_entity.UserStatsEntity

@Database(
    entities = [QuestionEntity::class, UserStatsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    abstract fun userStatsDao(): UserStatsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "appvet_database"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}