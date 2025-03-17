package com.usatayamish.expertcoursequizgame.load.data.cloud

import com.google.gson.annotations.SerializedName

data class QuestionAndChoicesCloud(
    @SerializedName("question")
    val question: String,
    @SerializedName("correct_answer")
    val correctAnswer: String,
    @SerializedName("incorrect_answers")
    val incorrectAnswers: List<String>
)