package com.usatayamish.expertcoursequizgame.core

import com.usatayamish.expertcoursequizgame.game.GameScreen
import com.usatayamish.expertcoursequizgame.load.presentation.LoadScreen

class MainViewModel(
    private val index: IntCache,
    private val size: Int
): MyViewModel<Unit> {

    fun firstScreen(isFirstRun: Boolean): Screen {
        return if (isFirstRun) {
            if(index.read() == size) {
                LoadScreen
            } else
                GameScreen
        } else
            Screen.Empty
    }
}