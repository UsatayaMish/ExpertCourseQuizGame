package com.usatayamish.expertcoursequizgame.game.di

import com.usatayamish.expertcoursequizgame.Core
import com.usatayamish.expertcoursequizgame.Module
import com.usatayamish.expertcoursequizgame.ProvideViewModel
import com.usatayamish.expertcoursequizgame.core.IntCache
import com.usatayamish.expertcoursequizgame.di.AbstractProvideViewModel
import com.usatayamish.expertcoursequizgame.game.GameRepository
import com.usatayamish.expertcoursequizgame.game.GameUiObservable
import com.usatayamish.expertcoursequizgame.game.GameViewModel

class GameModule(private val core: Core) : Module<GameViewModel> {

    override fun viewModel(): GameViewModel {
        val corrects = IntCache.Base(core.sharedPreferences, "corrects", 0)
        val incorrects = IntCache.Base(core.sharedPreferences, "incorrects", 0)
        val index = IntCache.Base(core.sharedPreferences, "indexKey", 0)
        val userChoiceIndex = IntCache.Base(core.sharedPreferences, "userChoiceIndexKey", -1)
        return GameViewModel(
            GameUiObservable.Base(),
            core.clearViewModel,
            if (core.runUiTests)
                GameRepository.Fake(
                    corrects,
                    incorrects,
                    index,
                    userChoiceIndex,
                )
            else {
                GameRepository.Base(
                    corrects,
                    incorrects,
                    index,
                    userChoiceIndex,
                    core.cacheModule.dao(),
                    core.cacheModule.clearDatabase(),
                    core.size
                )
            },
            core.runAsync
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