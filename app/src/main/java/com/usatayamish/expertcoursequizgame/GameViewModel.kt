package com.usatayamish.expertcoursequizgame

class GameViewModel(
    private val repository: GameRepository
) {

    fun init(): GameUiState {
        val data = repository.questionAndChoices()
        return GameUiState.AskedQuestion(
            data.question,
            data.choices
        )
    }

    fun chooseFirst(): GameUiState {
        repository.saveUserChoice(0)
        val data = repository.questionAndChoices()
        return GameUiState.ChoiceMade(
            data.question,
            listOf(
                ChoiceUiState.NotAvailableToChoose(data.choices[0]),
                ChoiceUiState.AvailableToChoose(data.choices[1]),
                ChoiceUiState.AvailableToChoose(data.choices[2]),
                ChoiceUiState.AvailableToChoose(data.choices[3])
            )
        )
    }

    fun chooseSecond(): GameUiState {
        repository.saveUserChoice(1)
        val data = repository.questionAndChoices()
        return GameUiState.ChoiceMade(
            data.question,
            listOf(
                ChoiceUiState.AvailableToChoose(data.choices[0]),
                ChoiceUiState.NotAvailableToChoose(data.choices[1]),
                ChoiceUiState.AvailableToChoose(data.choices[2]),
                ChoiceUiState.AvailableToChoose(data.choices[3])
            )
        )
    }

    fun chooseThird(): GameUiState {
        repository.saveUserChoice(2)
        val data = repository.questionAndChoices()
        return GameUiState.ChoiceMade(
            data.question,
            listOf(
                ChoiceUiState.AvailableToChoose(data.choices[0]),
                ChoiceUiState.AvailableToChoose(data.choices[1]),
                ChoiceUiState.NotAvailableToChoose(data.choices[2]),
                ChoiceUiState.AvailableToChoose(data.choices[3])
            )
        )
    }

    fun chooseFourth(): GameUiState {
        repository.saveUserChoice(3)
        val data = repository.questionAndChoices()
        return GameUiState.ChoiceMade(
            data.question,
            listOf(
                ChoiceUiState.AvailableToChoose(data.choices[0]),
                ChoiceUiState.AvailableToChoose(data.choices[1]),
                ChoiceUiState.AvailableToChoose(data.choices[2]),
                ChoiceUiState.NotAvailableToChoose(data.choices[3])
            )
        )
    }

    fun check(): GameUiState {
        val data = repository.questionAndChoices()
        val correctAndUserChoiceIndexes = repository.check()
        return GameUiState.AnswerChecked(
            data.question,
            data.choices.mapIndexed {index, choice ->
                if (correctAndUserChoiceIndexes.correctIndex == index) {
                    ChoiceUiState.Correct(choice)
                } else if (correctAndUserChoiceIndexes.userChoiceIndex == index) {
                    ChoiceUiState.Incorrect(choice)
                } else {
                    ChoiceUiState.NotAvailableToChoose(choice)
                }
            }
        )
    }

    fun next(): GameUiState {
        repository.next()
        return init()
    }

}
