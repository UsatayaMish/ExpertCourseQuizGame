package com.usatayamish.expertcoursequizgame.load.presentation

class HandleProcessDeath {

    private var needToHandleProcessDeathRightNow = true

    fun firstProcessStarted() {
        needToHandleProcessDeathRightNow = false
    }

    fun processDeathHappened() = needToHandleProcessDeathRightNow

    fun processDeathHandled() {
        needToHandleProcessDeathRightNow = false
    }
}