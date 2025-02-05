package com.usatayamish.expertcoursequizgame.load

import com.google.gson.Gson
import java.net.HttpURLConnection
import java.net.URL

interface LoadRepository {

    fun load(resultCallback: (LoadResult) -> Unit)

    class Base(
        private val parseQuestionAndChoices: ParseQuestionAndChoices,
        private val dataCache: StringCache,
    ) : LoadRepository {

        private val url = "https://opentdb.com/api.php?amount=10&type=multiple"

        override fun load(resultCallback: (LoadResult) -> Unit) {
            val connection = URL(url).openConnection() as HttpURLConnection
            try {
                val data = connection.inputStream.bufferedReader().use { it.readText() }

                val response = parseQuestionAndChoices.parse(data)
                if (response.response_code == 0) {
                    val list = response.results
                    if (list.isEmpty()) {
                        resultCallback.invoke(LoadResult.Error("empty data"))
                    } else {
                        dataCache.save(data)
                        resultCallback.invoke(LoadResult.Success)
                    }
                } else {
                    resultCallback.invoke(
                        LoadResult.Error(
                            "response code is not successful ${
                                handleResponseCode(
                                    response.response_code
                                )
                            }"
                        )
                    )
                }
            } catch (e: Exception) {
                resultCallback.invoke(LoadResult.Error(e.message ?: "error"))
            } finally {
                connection.disconnect()
            }
        }

        private fun handleResponseCode(code: Int): String {
            return when (code) {
                1 -> "No Results Could not return results. The API doesn't have enough questions for your query. (Ex. Asking for 50 Questions in a Category that only has 20.)"
                2 -> "Invalid Parameter Contains an invalid parameter. Arguements passed in aren't valid. (Ex. Amount = Five)"
                3 -> "Token Not Found Session Token does not exist."
                4 -> "Token Empty Session Token has returned all possible questions for the specified query. Resetting the Token is necessary."
                5 -> "Rate Limit Too many requests have occurred. Each IP can only access the API once every 5 seconds."
                else -> ""
            }
        }
    }
}

interface ParseQuestionAndChoices {

    fun parse(source: String): Response

    class Base(
        private val gson: Gson
    ): ParseQuestionAndChoices {

        override fun parse(source: String): Response {
            return gson.fromJson(source, Response::class.java)
        }
    }
}

data class Response(
    val response_code: Int,
    val results: List<QuestionAndChoicesCloud>
)

data class QuestionAndChoicesCloud(
     val question: String,
     val correct_answer: String,
     val incorrect_answers: List<String>
)