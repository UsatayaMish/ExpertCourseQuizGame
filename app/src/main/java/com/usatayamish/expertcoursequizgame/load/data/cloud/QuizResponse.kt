package com.usatayamish.expertcoursequizgame.load.data.cloud

import com.google.gson.annotations.SerializedName

data class QuizResponse(
    @SerializedName("response_code")
    val responseCode: Int,
    @SerializedName("results")
    val dataList: List<QuestionAndChoicesCloud>
)