package com.usatayamish.expertcoursequizgame.di

import com.usatayamish.expertcoursequizgame.Core
import com.usatayamish.expertcoursequizgame.Module
import com.usatayamish.expertcoursequizgame.MyViewModel
import com.usatayamish.expertcoursequizgame.ProvideViewModel

abstract class AbstractProvideViewModel(
    protected val core: Core,
    private val nextChain: ProvideViewModel,
    private val viewModelClass: Class<out MyViewModel>
) : ProvideViewModel {

    override fun <T : MyViewModel> makeViewModel(clasz: Class<T>): T {
        return if(clasz == viewModelClass) {
            module().viewModel() as T
        } else {
            nextChain.makeViewModel(clasz)
        }
    }

    protected abstract fun module(): Module<*>
}