package com.usatayamish.expertcoursequizgame

import com.usatayamish.expertcoursequizgame.core.MyViewModel

interface ClearViewModel {
    fun clear(viewModelClass: Class<out MyViewModel<*>>)
}