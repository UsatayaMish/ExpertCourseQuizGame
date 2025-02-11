package com.usatayamish.expertcoursequizgame.load.data

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizService {

    @GET("api.php")
    fun questionAndChoices(
        @Query("amount") amount: Int = 10,
        @Query("type") type: String = "multiple"
    ) : Call<QuizResponse>
}

data class QuizResponse(
    @SerializedName("response_code")
    val responseCode: Int,
    @SerializedName("results")
    val dataList: List<QuestionAndChoicesCloud>
)

data class QuestionAndChoicesCloud(
    @SerializedName("question")
    val question: String,
    @SerializedName("correct_answer")
    val correctAnswer: String,
    @SerializedName("incorrect_answers")
    val incorrectAnswers: List<String>
)