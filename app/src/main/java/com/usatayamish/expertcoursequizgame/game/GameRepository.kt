package com.usatayamish.expertcoursequizgame.game

import com.usatayamish.expertcoursequizgame.IntCache

interface GameRepository {

    fun questionAndChoices(): QuestionAndChoices

    fun saveUserChoice(index: Int)

    fun check(): CorrectAndIncorrectIndexes

    fun next()

    fun isLastQuestion(): Boolean

    class Base(
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


        override fun questionAndChoices(): QuestionAndChoices {
            return list[index.read()]
        }


        override fun saveUserChoice(index: Int) {
            userChoiceIndex.save(index)
        }

        override fun check(): CorrectAndIncorrectIndexes {
            return CorrectAndIncorrectIndexes(
                correctIndex = questionAndChoices().correctIndex,
                userChoiceIndex = this.userChoiceIndex.read()
            )
        }

        override fun next() {
            userChoiceIndex.save(-1)
            if (!isLastQuestion())
                index.save(index.read() + 1)
        }

        override fun isLastQuestion(): Boolean {
            return index.read() + 1 == list.size
        }
    }
}

