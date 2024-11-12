package com.usatayamish.expertcoursequizgame.game.di

import com.usatayamish.expertcoursequizgame.Core
import com.usatayamish.expertcoursequizgame.IntCache
import com.usatayamish.expertcoursequizgame.Module
import com.usatayamish.expertcoursequizgame.ProvideViewModel
import com.usatayamish.expertcoursequizgame.di.AbstractProvideViewModel
import com.usatayamish.expertcoursequizgame.game.GameRepository
import com.usatayamish.expertcoursequizgame.game.GameViewModel

class GameModule(private val core: Core) : Module<GameViewModel> {

    override fun viewModel(): GameViewModel {
        val corrects = IntCache.Base(core.sharedPreferences, "corrects", 0)
        val incorrects = IntCache.Base(core.sharedPreferences, "incorrects", 0)
        return GameViewModel(
            core.clearViewModel,
            GameRepository.Base(
                corrects,
                incorrects,
                IntCache.Base(core.sharedPreferences, "indexKey", 0),
                IntCache.Base(core.sharedPreferences, "userChoiceIndexKey", -1)
            )

        )
    }

}

class ProvideGameViewModel(
    core: Core,
    next: ProvideViewModel
) : AbstractProvideViewModel(
    core,
    next,
    GameViewModel::class.java
) {
    override fun module(): Module<*> = GameModule(core)

}