package com.usatayamish.expertcoursequizgame.load

import android.view.View
import android.widget.LinearLayout
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import com.usatayamish.expertcoursequizgame.R
import com.usatayamish.expertcoursequizgame.game.ButtonUi
import org.hamcrest.Matcher

class LoadPage {

    private val containerIdMatcher: Matcher<View> = withParent(withId(R.id.loadContainer))
    private val containerClassTypeMatcher: Matcher<View> =
        withParent(isAssignableFrom(LinearLayout::class.java))

    private val progressUi = ProgressUi(
        containerIdMatcher = containerIdMatcher,
        containerClassTypeMatcher = containerClassTypeMatcher
    )

    private val errorUi = ErrorUi(
        containerIdMatcher = containerIdMatcher,
        containerClassTypeMatcher = containerClassTypeMatcher
    )

    private val retryUi = ButtonUi(
        R.id.retryButton,
        R.string.retry,
        "#A020F0",
        containerIdMatcher = containerIdMatcher,
        containerClassTypeMatcher = containerClassTypeMatcher
    )

    fun assertProgressState() {
        errorUi.assertNotVisible()
        progressUi.assertVisible()
        retryUi.assertNotVisible()
    }

    fun waitTillError() {
        errorUi.waitTillVisible()
    }

    fun assertErrorState() {
        errorUi.assertVisible()
        progressUi.assertNotVisible()
        retryUi.assertVisible()
    }

    fun clickRetry() {
        retryUi.click()
    }

    fun waitTillGone() {
        errorUi.waitTillDoesNotExist()
    }

}
