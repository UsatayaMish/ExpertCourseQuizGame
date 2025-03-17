package com.usatayamish.expertcoursequizgame.stats.di

import com.usatayamish.expertcoursequizgame.Core
import com.usatayamish.expertcoursequizgame.Module
import com.usatayamish.expertcoursequizgame.ProvideViewModel
import com.usatayamish.expertcoursequizgame.core.IntCache
import com.usatayamish.expertcoursequizgame.di.AbstractProvideViewModel
import com.usatayamish.expertcoursequizgame.stats.GameOverViewModel
import com.usatayamish.expertcoursequizgame.stats.StatsRepository

class GameOverModule(private val core: Core) : Module<GameOverViewModel> {

    override fun viewModel(): GameOverViewModel {
        val corrects = IntCache.Base(core.sharedPreferences, "corrects", 0)
        val incorrects = IntCache.Base(core.sharedPreferences, "incorrects", 0)
        return GameOverViewModel(
            core.clearViewModel,
            StatsRepository.Base(
                corrects,
                incorrects
            )
        )
    }
}

class ProvideGameOverViewModel(
    core: Core,
    next: ProvideViewModel
): AbstractProvideViewModel(
    core,
    next,
    GameOverViewModel::class.java
) {

    override fun module(): Module<*> = GameOverModule(core)
}