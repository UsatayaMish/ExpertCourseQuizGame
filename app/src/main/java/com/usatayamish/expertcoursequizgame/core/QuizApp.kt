package com.usatayamish.expertcoursequizgame.core

import android.app.Application
import com.usatayamish.expertcoursequizgame.ClearViewModel
import com.usatayamish.expertcoursequizgame.Core
import com.usatayamish.expertcoursequizgame.ManageViewModels
import com.usatayamish.expertcoursequizgame.ProvideViewModel


class QuizApp : Application(), ProvideViewModel {

    private lateinit var factory: ManageViewModels

    override fun onCreate() {
        super.onCreate()
        val make = ProvideViewModel.Make(
            Core(
                this,
                object : ClearViewModel {
                    override fun clear(viewModelClass: Class<out MyViewModel<*>>) {
                        factory.clear(viewModelClass)
                    }
                }
            )
        )

        factory = ManageViewModels.Factory(make)
    }

    override fun <S: Any, T : MyViewModel<S>> makeViewModel(clasz: Class<T>): T {
        return factory.makeViewModel(clasz)
    }


}


