package com.usatayamish.expertcoursequizgame.game

import android.view.View
import android.widget.LinearLayout
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.usatayamish.expertcoursequizgame.R
import org.hamcrest.Matcher

class GamePage(
    question: String,
    choices: List<String>
) {

    private val containerIdMatcher: Matcher<View> = withParent(withId(R.id.rootLayout))
    private val containerClassTypeMatcher: Matcher<View> = withParent(isAssignableFrom(LinearLayout::class.java))

    private val questionUi = QuestionUi(
        text = question,
        containerIdMatcher = containerIdMatcher,
        containerClassTypeMatcher = containerClassTypeMatcher
    )

    private val choicesUiList = choices.map {
        ChoiceUi(
            text = it,
            containerIdMatcher = containerIdMatcher,
            containerClassTypeMatcher = containerClassTypeMatcher
        )
    }

    private val checkUi = ButtonUi(
        id = R.id.checkButton,
        textResId = R.string.check,
        colorHex = "#BC25E1",
        containerIdMatcher = containerIdMatcher,
        containerClassTypeMatcher = containerClassTypeMatcher
    )
    private val nextUi = ButtonUi(
        id = R.id.nextButton,
        textResId = R.string.next,
        colorHex = "#52CCF8",
        containerIdMatcher = containerIdMatcher,
        containerClassTypeMatcher = containerClassTypeMatcher
    )

    fun assertAskedQuestionState() {
        questionUi.assertTextVisible()
        choicesUiList.forEach {
            it.assertAvailableToChooseState()
        }
        checkUi.assertNotVisible()
        nextUi.assertNotVisible()
    }

    fun clickFirstChoice() {
        choicesUiList.first().click()
    }

    fun assertFirstChoiceMadeState() {
        questionUi.assertTextVisible()
        choicesUiList.first().assertNotAvailableToChooseState()
        for (i in 1 until choicesUiList.size) {
            choicesUiList[i].assertAvailableToChooseState()
        }
        checkUi.assertVisible()
        nextUi.assertNotVisible()
    }

    fun clickCheck() {
        checkUi.click()
    }

    fun assertAnswerCheckedStateFirstIsCorrect() {
        questionUi.assertTextVisible()
        choicesUiList.first().assertCorrectState()
        for (i in 1 until choicesUiList.size) {
            choicesUiList[i].assertAvailableToChooseState()
        }
        checkUi.assertNotVisible()
        nextUi.assertVisible()
    }

    fun clickSecondChoice() {
        choicesUiList[1].click()
    }

    fun assertSecondChoiceMadeState() {
        questionUi.assertTextVisible()
        choicesUiList.forEachIndexed { index, choiceUi ->
            if(index == 1) {
                choiceUi.assertNotAvailableToChooseState()
            } else {
                choiceUi.assertAvailableToChoose()
            }
        }
        checkUi.assertVisible()
        nextUi.assertNotVisible()
    }

    fun assertAnswerCheckedStateFirstIsCorrectSecondIsIncorrect() {
        questionUi.assertTextVisible()
        choicesUiList.first().assertCorrectState()
        choicesUiList[1].assertIncorrectState()
        choicesUiList[2].assertNotAvailableToChooseState()
        choicesUiList[3].assertNotAvailableToChooseState()
        checkUi.assertNotVisible()
        nextUi.asserVisible()
    }

    fun clickNext() {
        nextUi.click()
    }

}