package com.usatayamish.expertcoursequizgame.load

import android.view.View
import android.widget.ProgressBar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.usatayamish.expertcoursequizgame.R
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not

class ProgressUi(
    containerIdMatcher: Matcher<View>,
    containerClassTypeMatcher: Matcher<View>
) {
    private val interaction: ViewInteraction = onView(
        allOf(
            withId(R.id.progressBar),
            isAssignableFrom(ProgressBar::class.java),
            containerIdMatcher,
            containerClassTypeMatcher
        )
    )

    fun assertVisible() {
        interaction.check(matches(isCompletelyDisplayed()))
    }

    fun assertNotVisible() {
        interaction.check(matches(not(isDisplayed())))
    }
}
