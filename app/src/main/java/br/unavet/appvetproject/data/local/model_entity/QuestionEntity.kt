package br.unavet.appvetproject.data.local.model_entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.unavet.appvetproject.common.Difficulty
import kotlinx.serialization.Serializable

@Entity(tableName = "question_table")
@Serializable
data class QuestionEntity(
    @PrimaryKey val id: String,
    val question: String,
    val answer: String,
    val imageUrl: String? = null,
    val difficulty: Difficulty
)