package com.usatayamish.expertcoursequizgame

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule


@RunWith(AndroidJUnit4::class)
class ScenarioTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var gamePage: GamePage

    @Before
    fun setup() {
        gamePage = GamePage(
            question = "what color is the sky",
            choices = listOf(
                "blue",
                "green",
                "red",
                "yellow"
            )
        )
    }

    /**
     * QGTC-01
     */
    @Test
    fun caseNumber1() {
        gamePage.assertAskedQuestionState()

        gamePage.clickFirstChoice()
        gamePage.assertFirstChoiceMadeState()

        gamePage.clickCheck()
        gamePage.assertAnswerCheckedStateFirstIsCorrect()
    }

    /**
     * QGTC-02
     */
    @Test
    fun caseNumber2() {
        gamePage.assertAskedQuestionState()

        gamePage.clickFirstChoice()
        gamePage.assertFirstChoiceMadeState()

        gamePage.clickSecondChoice()
        gamePage.assertSecondChoiceMadeState()

        gamePage.clickCheck()
        gamePage.assertAnswerCheckedStateFirstIsCorrectSecondIsIncorrect()

        gamePage.clickNext()

        gamePage = GamePage(
            question = "what color is the grass",
            choices = listOf(
                "green",
                "blue",
                "yellow",
                "red"
            )
        )

        gamePage.assertAskedQuestionState()

    }

}