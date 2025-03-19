package com.usatayamish.expertcoursequizgame.core

import com.usatayamish.expertcoursequizgame.Core
import com.usatayamish.expertcoursequizgame.Module
import com.usatayamish.expertcoursequizgame.ProvideViewModel
import com.usatayamish.expertcoursequizgame.di.AbstractProvideViewModel

class MainModule(
    private val core: Core
): Module<MainViewModel> {

    override fun viewModel(): MainViewModel {
        return MainViewModel(
            IntCache.Base(core.sharedPreferences, "indexKey", core.size),
            core.size
        )
    }
}

class ProvideMainViewModel(
    core: Core,
    next: ProvideViewModel
): AbstractProvideViewModel(
    core,
    next,
    MainViewModel::class.java
) {

    override fun module(): Module<*> = MainModule(core)
}