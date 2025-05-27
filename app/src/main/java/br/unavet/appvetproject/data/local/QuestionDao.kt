package br.unavet.appvetproject.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.unavet.appvetproject.common.Difficulty
import br.unavet.appvetproject.data.local.model_entity.QuestionEntity

@Dao
interface QuestionDao {

    @Query("SELECT * FROM question_table WHERE difficulty = :difficulty")
    suspend fun getQuestionsByDifficulty(difficulty: Difficulty): List<QuestionEntity>

    @Query("SELECT * FROM question_table")
    suspend fun getAllQuestions(): List<QuestionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<QuestionEntity>)

    @Query("DELETE FROM question_table")
    suspend fun clearQuestions()
}