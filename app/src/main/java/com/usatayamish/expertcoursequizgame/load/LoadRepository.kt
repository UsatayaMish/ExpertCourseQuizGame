package com.usatayamish.expertcoursequizgame.load

interface LoadRepository {

    fun load(resultCallback: (LoadResult) -> Unit)
}
