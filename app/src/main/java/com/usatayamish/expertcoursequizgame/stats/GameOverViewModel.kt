package com.usatayamish.expertcoursequizgame.stats

import com.usatayamish.expertcoursequizgame.ClearViewModel
import com.usatayamish.expertcoursequizgame.MyViewModel
import com.usatayamish.expertcoursequizgame.views.stats.StatsUiState

class GameOverViewModel(
    private val clearViewModel: ClearViewModel,
    private val repository: StatsRepository) : MyViewModel{

    fun init(isFirstRun: Boolean): StatsUiState {
        if (isFirstRun) {
            val (corrects, incorrects) = repository.stats()
            repository.clear()
            return StatsUiState.Base(corrects, incorrects)
        } else {
            return StatsUiState.Empty
        }
    }

    fun clear() {
        clearViewModel.clear(GameOverViewModel::class.java)
    }
}