package com.usatayamish.expertcoursequizgame

import android.view.View
import com.usatayamish.expertcoursequizgame.databinding.ActivityMainBinding
import java.io.Serializable

interface GameUiState : Serializable {

    fun update(binding: ActivityMainBinding)

    abstract class Abstract(
        private val questionText: String,
        private val choicesStateList: List<ChoiceUiState>,
        private val checkVisibility: Int,
        private val nextVisibility: Int
    ) : GameUiState {

        override fun update(binding: ActivityMainBinding) = with(binding) {
            questionTextView.text = questionText
            choicesStateList[0].update(firstChoiceButton)
            choicesStateList[1].update(secondChoiceButton)
            choicesStateList[2].update(thirdChoiceButton)
            choicesStateList[3].update(fourthChoiceButton)
            checkButton.visibility = checkVisibility
            nextButton.visibility = nextVisibility
        }
    }


    data class AskedQuestion(
        private val question: String,
        private val choices: List<String>
    ) : Abstract(
        question,
        choices.map {ChoiceUiState.AvailableToChoose(it)},
        checkVisibility = View.GONE,
        nextVisibility = View.GONE
    )


    data class ChoiceMade(
        private val question: String,
        private val choices: List<ChoiceUiState>
    ) : Abstract(
        question,
        choices,
        checkVisibility = View.VISIBLE,
        nextVisibility = View.GONE
    )


    data class AnswerChecked(
        private val question: String,
        private val choices: List<ChoiceUiState>
    ) : Abstract(
        question,
        choices,
        checkVisibility = View.GONE,
        nextVisibility = View.VISIBLE
    )

}

