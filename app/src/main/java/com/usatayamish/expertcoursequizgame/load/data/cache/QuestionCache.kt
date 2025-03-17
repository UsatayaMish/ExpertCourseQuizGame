package com.usatayamish.expertcoursequizgame.load.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class QuestionCache(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("question")
    val question: String,
    @ColumnInfo("correct")
    val correctAnswer: String,

)

@Entity(tableName = "incorrects", primaryKeys = ["questionId", "text"])
data class IncorrectCache(
    @ColumnInfo("questionId")
    val questionId: Int,
    @ColumnInfo("text")
    val text: String
)
