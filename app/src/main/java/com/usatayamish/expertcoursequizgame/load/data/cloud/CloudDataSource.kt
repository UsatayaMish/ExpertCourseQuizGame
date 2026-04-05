package com.usatayamish.expertcoursequizgame.load.data.cloud

interface CloudDataSource {

    suspend fun load(): List<QuestionAndChoicesCloud>

    class Base(
        private val service: QuizService,
        private val size: Int

    ) : CloudDataSource {

        override suspend fun load(): List<QuestionAndChoicesCloud> {
            val result = service.questionAndChoices(size).execute()
            if (result.isSuccessful) {
                val body = result.body()!!
                if (body.responseCode == 0) {
                    val list = body.dataList
                    if (list.isEmpty()) {
                        throw IllegalStateException("service unavailable")
                    } else {
                        return list
                    }
                } else {
                    throw IllegalArgumentException(handleResponseCode(body.responseCode))
                }
            } else {

                throw IllegalStateException(result.errorBody().toString())
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