package com.usatayamish.expertcoursequizgame

import com.usatayamish.expertcoursequizgame.core.MyViewModel
import com.usatayamish.expertcoursequizgame.game.di.ProvideGameViewModel
import com.usatayamish.expertcoursequizgame.load.di.ProvideLoadViewModel
import com.usatayamish.expertcoursequizgame.stats.di.ProvideGameOverViewModel

interface ProvideViewModel {

    fun <S: Any, T : MyViewModel<S>> makeViewModel(clasz: Class<T>): T

    class Make(
        core: Core
    ) : ProvideViewModel {

        private var chain: ProvideViewModel

        init {
            chain = Error()
            chain = ProvideLoadViewModel(core, chain)
            chain = ProvideGameViewModel(core, chain)
            chain = ProvideGameOverViewModel(core, chain)
        }

        override fun <S: Any, T : MyViewModel<S>> makeViewModel(clasz: Class<T>): T {
            return chain.makeViewModel(clasz)
        }
    }

    class Error : ProvideViewModel {

        override fun <S: Any, T : MyViewModel<S>> makeViewModel(clasz: Class<T>): T {
            throw IllegalStateException("unknown class $clasz")
        }
    }
}