package com.usatayamish.expertcoursequizgame.load.data

import android.util.Log
import com.usatayamish.expertcoursequizgame.core.IntCache
import com.usatayamish.expertcoursequizgame.load.data.cache.IncorrectCache
import com.usatayamish.expertcoursequizgame.load.data.cache.QuestionAndChoicesDao
import com.usatayamish.expertcoursequizgame.load.data.cache.QuestionCache
import com.usatayamish.expertcoursequizgame.load.data.cloud.CloudDataSource
import kotlinx.coroutines.delay
import okio.IOException

interface LoadRepository {

    suspend fun load(timeStamp: Long)

    class Base(
        private val index: IntCache,
        private val cloudDataSource: CloudDataSource,
        private val cacheDataSource: QuestionAndChoicesDao,
    ) : LoadRepository {

        override suspend fun load(timeStamp: Long) {
            try {
                Log.d("usatayamish", "timestamp is $timeStamp")
                val dataList = cloudDataSource.load()
                delay(10_000)
                val incorrects = mutableListOf<IncorrectCache>()
                val questions: List<QuestionCache> =
                    dataList.mapIndexed { index, data ->
                        val temporary = data.incorrectAnswers.map {
                            IncorrectCache(questionId = index, text = it)
                        }
                        incorrects.addAll(temporary)
                        QuestionCache(index, data.question, data.correctAnswer)
                    }
                cacheDataSource.insertQuestions(questions)
                cacheDataSource.insertIncorrects(incorrects)
                index.save(0)
            } catch (e: Exception) {
                if (e is IOException)
                    throw NoInternetConnectionException()
                if (e is IllegalArgumentException)
                    throw BackendException(e.message?:"")
                throw ServiceUnavailable()
            }
        }



    }

    class FakeRepository : LoadRepository {

        private var count = 0

        override suspend fun load(timeStamp: Long) {
            delay(3000)
            if (count == 0) {
                count++
                throw NoInternetConnectionException()
            } else {
                LoadResult.Success
            }
        }
    }
}

class NoInternetConnectionException : Exception()

class BackendException(message: String) : Exception(message)

class ServiceUnavailable : Exception()

