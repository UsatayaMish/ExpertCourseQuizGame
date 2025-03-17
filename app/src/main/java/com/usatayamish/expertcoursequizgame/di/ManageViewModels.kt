package com.usatayamish.expertcoursequizgame

import com.usatayamish.expertcoursequizgame.core.MyViewModel

interface ManageViewModels: ProvideViewModel, ClearViewModel {

    class Factory(
        private val make: ProvideViewModel
    ) : ManageViewModels {

        private val viewModelsMap = mutableMapOf<Class<out MyViewModel<*>>, MyViewModel<*>?>()

        override fun <S: Any, T : MyViewModel<S>> makeViewModel(clasz: Class<T>): T =
            if (viewModelsMap[clasz] == null) {
                val viewModel = make.makeViewModel(clasz)
                viewModelsMap[clasz] = viewModel
                viewModel
            } else {
                viewModelsMap[clasz] as T
            }

        override fun clear(viewModelClass: Class<out MyViewModel<*>>) {
            viewModelsMap[viewModelClass] = null
        }


    }
}