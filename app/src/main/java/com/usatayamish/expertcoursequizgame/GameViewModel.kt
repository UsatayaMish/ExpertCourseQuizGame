package com.usatayamish.expertcoursequizgame

import com.usatayamish.expertcoursequizgame.views.choice.ChoiceUiState

class GameViewModel(
    private val repository: GameRepository
) {

    fun init(firstRun: Boolean = true): GameUiState {
            if (firstRun) {
            val data = repository.questionAndChoices()
            return GameUiState.AskedQuestion(
                data.question,
                data.choices
            )
        } else {
            return GameUiState.Empty
        }
    }

    fun chooseFirst(): GameUiState {
        repository.saveUserChoice(0)
        val data = repository.questionAndChoices()
        return GameUiState.ChoiceMade(
            data.choices.mapIndexed{ index, _ ->
                if(index == 0)
                    ChoiceUiState.NotAvailableToChoose
                else
                    ChoiceUiState.AvailableToChoose
            }
        )
    }

    fun chooseSecond(): GameUiState {
        repository.saveUserChoice(1)
        val data = repository.questionAndChoices()
        return GameUiState.ChoiceMade(
            data.choices.mapIndexed{ index, _ ->
                if(index == 1)
                    ChoiceUiState.NotAvailableToChoose
                else
                    ChoiceUiState.AvailableToChoose
            }
        )
    }

    fun chooseThird(): GameUiState {
        repository.saveUserChoice(2)
        val data = repository.questionAndChoices()
        return GameUiState.ChoiceMade(
            data.choices.mapIndexed{ index, _ ->
                if(index == 2)
                    ChoiceUiState.NotAvailableToChoose
                else
                    ChoiceUiState.AvailableToChoose
            }
        )
    }

    fun chooseFourth(): GameUiState {
        repository.saveUserChoice(3)
        val data = repository.questionAndChoices()
        return GameUiState.ChoiceMade(
            data.choices.mapIndexed{ index, _ ->
                if(index == 3)
                    ChoiceUiState.NotAvailableToChoose
                else
                    ChoiceUiState.AvailableToChoose
            }
        )
    }

    fun check(): GameUiState {
        val data = repository.questionAndChoices()
        val correctAndUserChoiceIndexes = repository.check()
        return GameUiState.AnswerChecked(
            data.choices.mapIndexed { index, _ ->
                if (correctAndUserChoiceIndexes.correctIndex == index) {
                    ChoiceUiState.Correct
                } else if (correctAndUserChoiceIndexes.userChoiceIndex == index) {
                    ChoiceUiState.Incorrect
                } else {
                    ChoiceUiState.NotAvailableToChoose
                }
            }
        )
    }

    fun next(): GameUiState {
        repository.next()
        return init()
    }

}
