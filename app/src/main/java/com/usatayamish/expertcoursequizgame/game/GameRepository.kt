package com.usatayamish.expertcoursequizgame.game

import com.usatayamish.expertcoursequizgame.core.IntCache
import com.usatayamish.expertcoursequizgame.load.data.cache.ClearDatabase
import com.usatayamish.expertcoursequizgame.load.data.cache.QuestionAndChoicesDao

interface GameRepository {

    suspend fun questionAndChoices(): QuestionAndChoices

    fun saveUserChoice(index: Int)

    suspend fun check(): CorrectAndIncorrectIndexes

    fun next()

    fun isLastQuestion(): Boolean

    suspend fun clear()

    class Base(
        private val corrects: IntCache,
        private val incorrects: IntCache,
        private val index: IntCache,
        private val userChoiceIndex: IntCache,
        private val dao: QuestionAndChoicesDao,
        private val clearDatabase: ClearDatabase,
        private val size: Int
    ) : GameRepository {

        override suspend fun questionAndChoices(): QuestionAndChoices {
            val id = index.read()
            val question = dao.question(id)
            val incorrects = dao.incorrects(id)
            val choices = (listOf(question.correctAnswer) + incorrects.map { it.text }).shuffled()
            return QuestionAndChoices(
                question.question,
                choices,
                choices.indexOf(question.correctAnswer)
            )
        }

        override fun saveUserChoice(index: Int) {
            userChoiceIndex.save(index)
        }

        override suspend fun check(): CorrectAndIncorrectIndexes {
            val correctIndex = questionAndChoices().correctIndex

            if (userChoiceIndex.read() == correctIndex) {
                corrects.save(corrects.read() + 1)
            } else {
                incorrects.save(incorrects.read() + 1)
            }

            return CorrectAndIncorrectIndexes(
                correctIndex = correctIndex,
                userChoiceIndex = this.userChoiceIndex.read()
            )
        }

        override fun next() {
            userChoiceIndex.save(-1)
            index.save(index.read() + 1)
        }

        override fun isLastQuestion(): Boolean = index.read() == size

        override suspend fun clear() {
            userChoiceIndex.save(-1)
            index.save(0)
            clearDatabase.clear()
        }
    }

    class Fake(
        private val corrects: IntCache,
        private val incorrects: IntCache,
        private val index: IntCache,
        private val userChoiceIndex: IntCache,
        private val list: List<QuestionAndChoices> = listOf(
            QuestionAndChoices(
                question = "What color is the sky?",
                choices = listOf(
                    "blue",
                    "green",
                    "red",
                    "yellow"
                ),
                correctIndex = 0
            ),
            QuestionAndChoices(
                question = "what color is the grass",
                choices = listOf(
                    "green",
                    "blue",
                    "yellow",
                    "red"
                ),
                correctIndex = 0
            )
        )
    ) : GameRepository {

        override suspend fun questionAndChoices(): QuestionAndChoices {
            return list[index.read()]
        }


        override fun saveUserChoice(index: Int) {
            userChoiceIndex.save(index)
        }

        override suspend fun check(): CorrectAndIncorrectIndexes {
            val correctIndex = questionAndChoices().correctIndex

            if (userChoiceIndex.read() == correctIndex) {
                corrects.save(corrects.read() + 1)
            } else {
                incorrects.save(incorrects.read() + 1)
            }

            return CorrectAndIncorrectIndexes(
                correctIndex = correctIndex,
                userChoiceIndex = this.userChoiceIndex.read()
            )
        }

        override fun next() {
            userChoiceIndex.save(-1)
            index.save(index.read() + 1)
        }

        override fun isLastQuestion(): Boolean {
            return index.read() == list.size
        }

        override suspend fun clear() {
            userChoiceIndex.save(-1)
            index.save(0)
        }
    }
}

