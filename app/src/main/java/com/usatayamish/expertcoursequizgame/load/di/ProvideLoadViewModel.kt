package com.usatayamish.expertcoursequizgame.load.di

import com.usatayamish.expertcoursequizgame.Core
import com.usatayamish.expertcoursequizgame.Module
import com.usatayamish.expertcoursequizgame.ProvideViewModel
import com.usatayamish.expertcoursequizgame.RunAsync
import com.usatayamish.expertcoursequizgame.di.AbstractProvideViewModel
import com.usatayamish.expertcoursequizgame.load.data.LoadRepository
import com.usatayamish.expertcoursequizgame.load.data.ParseQuestionAndChoices
import com.usatayamish.expertcoursequizgame.load.data.Response
import com.usatayamish.expertcoursequizgame.load.data.StringCache
import com.usatayamish.expertcoursequizgame.load.presentation.LoadViewModel
import com.usatayamish.expertcoursequizgame.load.presentation.UiObservable

class ProvideLoadViewModel(
    core: Core,
    next: ProvideViewModel
): AbstractProvideViewModel(
    core,
    next,
    LoadViewModel::class.java
) {

    override fun module(): Module<*> = LoadModule(core)
}

class LoadModule(
    private val core: Core
) : Module<LoadViewModel> {

    override fun viewModel(): LoadViewModel {
        val defaultResponse = core.gson.toJson(Response(-1, emptyList()))
        val dataCache = StringCache.Base(core.sharedPreferences, "responseData", defaultResponse)
        return LoadViewModel(
            LoadRepository.Base(
                ParseQuestionAndChoices.Base(core.gson),
                dataCache
            ),
            UiObservable.Base(),
            RunAsync.Base()
        )
    }
}