package com.usatayamish.expertcoursequizgame.stats

import android.view.View
import android.widget.FrameLayout
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.usatayamish.expertcoursequizgame.R
import com.usatayamish.expertcoursequizgame.game.ButtonUi
import org.hamcrest.Matcher

class GameOverPage(incorrects: Int, corrects: Int) {

    private val containerIdMatcher: Matcher<View> = withParent(withId(R.id.gameOverContainer))
    private val classTypeMatcher: Matcher<View> =
        withParent(isAssignableFrom(FrameLayout::class.java))

    private val statsUi = StatsUi(incorrects = incorrects, corrects = corrects, containerIdMatcher, classTypeMatcher)

    private val newGameUi = ButtonUi(
        R.id.newGameButton,
        R.string.new_game,
        "#2A24D9",
        containerIdMatcher,
        classTypeMatcher
    )

    fun assertInitialState() {
        statsUi.assertVisible()
        newGameUi.assertVisible()
    }

    fun clickNewGame() {
        newGameUi.click()
    }

    fun assertDoesNotExist() {
        statsUi.assertDoesNotExist()
    }


}



