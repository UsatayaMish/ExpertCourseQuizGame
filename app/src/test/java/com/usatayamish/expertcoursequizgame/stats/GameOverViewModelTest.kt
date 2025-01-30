package com.usatayamish.expertcoursequizgame.stats

import com.usatayamish.expertcoursequizgame.game.FakeClearViewModel
import com.usatayamish.expertcoursequizgame.views.stats.StatsUiState
import org.junit.Assert.assertEquals
import org.junit.Test


class GameOverViewModelTest{

    @Test
    fun test() {
        val repository = FakeRepository()
        val viewModel = GameOverViewModel(FakeClearViewModel(), repository = repository)
        

        assertEquals(StatsUiState.Base(2, 3), viewModel.init(isFirstRun = true))
        assertEquals(1, repository.clearCalledCount)


        assertEquals(StatsUiState.Empty, viewModel.init(isFirstRun = false))
        assertEquals(1, repository.clearCalledCount)
    }
}

private class FakeRepository: StatsRepository {

    override fun stats(): Pair<Int, Int> = Pair(2, 3)

    var clearCalledCount = 0
    override fun clear() {
        clearCalledCount++
    }
}
