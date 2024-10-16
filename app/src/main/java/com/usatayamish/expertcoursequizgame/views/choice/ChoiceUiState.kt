package com.usatayamish.expertcoursequizgame.views.choice

import java.io.Serializable

interface ChoiceUiState : Serializable {


    fun update(update: UpdateChoiceButton)

    abstract class Abstract(
        private val color: String,
        private val clickable: Boolean,
        private val enabled: Boolean
    ) : ChoiceUiState {


        override fun update(update: UpdateChoiceButton) {
            update.update(color, clickable, enabled)
        }
    }

    object NotAvailableToChoose : Abstract("#75797E", false, false)

    object AvailableToChoose : Abstract("#4280DC", true, true)

    data class Initial(
        private val text: String
    ) : Abstract("#4280DC", true, true) {

        override fun update(update: UpdateChoiceButton) {
            super.update(update)
            update.update(text)
        }
    }

    object Correct : Abstract("#3FD06D", false, true)

    object Incorrect : Abstract("#D53131", false, true)

}