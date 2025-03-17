package com.usatayamish.expertcoursequizgame.load.data

import com.usatayamish.expertcoursequizgame.load.data.cache.IncorrectCache
import com.usatayamish.expertcoursequizgame.load.data.cache.QuestionAndChoicesDao
import com.usatayamish.expertcoursequizgame.load.data.cache.QuestionCache
import com.usatayamish.expertcoursequizgame.load.data.cloud.QuizService
import kotlinx.coroutines.delay

interface LoadRepository {

    suspend fun load(): LoadResult

    class Base(
        private val service: QuizService,
        private val dao: QuestionAndChoicesDao,
        private val size: Int
    ) : LoadRepository {

        override suspend fun load(): LoadResult {
            try {
                val result = service.questionAndChoices(size).execute()
                if (result.isSuccessful) {
                    val body = result.body()!!
                    if (body.responseCode == 0) {
                        val list = body.dataList
                        if (list.isEmpty()) {
                            return LoadResult.Error("empty data")
                        } else {
                            val questions: List<QuestionCache> = body.dataList.mapIndexed { index, data ->
                                val incorrects = data.incorrectAnswers.map {
                                    IncorrectCache(questionId =  index, text = it)
                                }
                                dao.insertIncorrects(incorrects)
                                QuestionCache(index, data.question, data.correctAnswer)
                            }
                            dao.insertQuestions(questions)
                            return (LoadResult.Success)
                        }
                    } else {
                        return (LoadResult.Error(
                            "response code is not successful ${
                                handleResponseCode(
                                    body.responseCode
                                )
                            }"
                        ))
                    }
                } else {
                    return (LoadResult.Error(
                        "response code is not successful ${
                            handleResponseCode(
                                result.body()!!.responseCode
                            )
                        }"
                    ))
                }
            } catch (e: Exception) {
                return (LoadResult.Error(e.message ?: "error"))
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

    class FakeRepository : LoadRepository {

        private var count = 0

        override suspend fun load(): LoadResult {
            delay(3000)
            return if (count == 0) {
                count++
                LoadResult.Error("")
            } else {
                LoadResult.Success
            }
        }
    }
}


