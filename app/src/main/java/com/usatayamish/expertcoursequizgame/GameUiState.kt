package com.usatayamish.expertcoursequizgame


import com.usatayamish.expertcoursequizgame.views.choice.ChoiceUiState
import com.usatayamish.expertcoursequizgame.views.choice.UpdateChoiceButton
import com.usatayamish.expertcoursequizgame.views.question.UpdateText
import com.usatayamish.expertcoursequizgame.views.visiblebutton.UpdateVisibility
import com.usatayamish.expertcoursequizgame.views.visiblebutton.VisibilityUiState


interface GameUiState {

    fun update(
        questionTextView: UpdateText,
        firstChoiceButton: UpdateChoiceButton,
        secondChoiceButton: UpdateChoiceButton,
        thirdChoiceButton: UpdateChoiceButton,
        fourthChoiceButton: UpdateChoiceButton,
        nextButton: UpdateVisibility,
        checkButton: UpdateVisibility
    )

    object Empty : GameUiState {

        override fun update(
            questionTextView: UpdateText,
            firstChoiceButton: UpdateChoiceButton,
            secondChoiceButton: UpdateChoiceButton,
            thirdChoiceButton: UpdateChoiceButton,
            fourthChoiceButton: UpdateChoiceButton,
            nextButton: UpdateVisibility,
            checkButton: UpdateVisibility
        ) = Unit
    }



    data class AskedQuestion(
        private val question: String,
        private val choices: List<String>
    ) : GameUiState {

        override fun update(
            questionTextView: UpdateText,
            firstChoiceButton: UpdateChoiceButton,
            secondChoiceButton: UpdateChoiceButton,
            thirdChoiceButton: UpdateChoiceButton,
            fourthChoiceButton: UpdateChoiceButton,
            nextButton: UpdateVisibility,
            checkButton: UpdateVisibility
        ) {
            questionTextView.update(question)
            firstChoiceButton.update(ChoiceUiState.Initial(choices[0]))
            secondChoiceButton.update(ChoiceUiState.Initial(choices[1]))
            thirdChoiceButton.update(ChoiceUiState.Initial(choices[2]))
            fourthChoiceButton.update(ChoiceUiState.Initial(choices[3]))
            nextButton.update(VisibilityUiState.Gone)
            checkButton.update(VisibilityUiState.Gone)
        }


    }


    data class ChoiceMade(
        private val choices: List<ChoiceUiState>
    ) : GameUiState{

        override fun update(
            questionTextView: UpdateText,
            firstChoiceButton: UpdateChoiceButton,
            secondChoiceButton: UpdateChoiceButton,
            thirdChoiceButton: UpdateChoiceButton,
            fourthChoiceButton: UpdateChoiceButton,
            nextButton: UpdateVisibility,
            checkButton: UpdateVisibility
        ) {
            firstChoiceButton.update(choices[0])
            secondChoiceButton.update(choices[1])
            thirdChoiceButton.update(choices[2])
            fourthChoiceButton.update(choices[3])
            checkButton.update(VisibilityUiState.Visible)
        }
    }


    data class AnswerChecked(
        private val choices: List<ChoiceUiState>
    ) : GameUiState {

        override fun update(
            questionTextView: UpdateText,
            firstChoiceButton: UpdateChoiceButton,
            secondChoiceButton: UpdateChoiceButton,
            thirdChoiceButton: UpdateChoiceButton,
            fourthChoiceButton: UpdateChoiceButton,
            nextButton: UpdateVisibility,
            checkButton: UpdateVisibility
        ) {
            firstChoiceButton.update(choices[0])
            secondChoiceButton.update(choices[1])
            thirdChoiceButton.update(choices[2])
            fourthChoiceButton.update(choices[3])
            checkButton.update(VisibilityUiState.Gone)
            nextButton.update(VisibilityUiState.Visible)
        }
    }

}

