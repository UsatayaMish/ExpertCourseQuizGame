package com.usatayamish.expertcoursequizgame

interface Module<T : MyViewModel> {

    fun viewModel(): T
}