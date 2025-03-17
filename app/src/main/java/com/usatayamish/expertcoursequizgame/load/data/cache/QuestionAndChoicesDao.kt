package com.usatayamish.expertcoursequizgame.load.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuestionAndChoicesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(question: List<QuestionCache>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIncorrects(incorrect: List<IncorrectCache>)

    @Query("select * from questions where id=:questionId")
    suspend fun question(questionId: Int): QuestionCache

    @Query("select * from incorrects where questionId=:questionId")
    suspend fun incorrects(questionId: Int): List<IncorrectCache>


}