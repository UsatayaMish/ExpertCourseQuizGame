package com.usatayamish.expertcoursequizgame.stats

import com.usatayamish.expertcoursequizgame.views.stats.StatsUiState

class GameOverViewModel(private val repository: StatsRepository) {
    fun statsUiState() : StatsUiState {
        val (corrects, incorrects) = repository.stats()
        return StatsUiState.Base(corrects, incorrects)
    }
}