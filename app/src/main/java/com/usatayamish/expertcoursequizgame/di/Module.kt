package com.usatayamish.expertcoursequizgame

import com.usatayamish.expertcoursequizgame.core.MyViewModel

interface Module<T : MyViewModel<*>> {

    fun viewModel(): T
}