package com.usatayamish.expertcoursequizgame.views.error

import android.view.View
import java.io.Serializable

interface ErrorUiState: Serializable {

    fun update(updateError: UpdateError)

    abstract class Abstract(private val visibility: Int): ErrorUiState {

        override fun update(updateError: UpdateError) {
            updateError.updateVisibility(visibility)
        }
    }

    object Hide: Abstract(View.GONE)

    data class Show(private val resId: Int): Abstract(View.VISIBLE) {

        override fun update(updateError: UpdateError) {
            super.update(updateError)
            updateError.updateText(resId)
        }
    }
}