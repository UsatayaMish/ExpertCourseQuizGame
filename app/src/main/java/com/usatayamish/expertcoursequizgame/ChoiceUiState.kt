package com.usatayamish.expertcoursequizgame

import android.graphics.Color
import androidx.appcompat.widget.AppCompatButton
import java.io.Serializable

interface ChoiceUiState : Serializable {

    fun update(button: AppCompatButton)

    abstract class Abstract(
        private val value: String,
        private val color: String,
        private val clickable: Boolean,
        private val enabled: Boolean
    ) : ChoiceUiState {

        override fun update(button: AppCompatButton) = with(button){
            text = value
            isEnabled = enabled
            isClickable = clickable
            setBackgroundColor(Color.parseColor(color))

        }
    }

    data class NotAvailableToChoose(
        private val text: String
    ) : Abstract(text, "#75797E", false, false)

    data class AvailableToChoose(
        private val text: String
    ) : Abstract(text, "#4280DC", true, true)

    data class Correct(
        private val text: String
    ) : Abstract(text, "#3FD06D", false, true)

    data class Incorrect(
        private val text: String
    ) : Abstract(text, "#D53131", false, true)

}