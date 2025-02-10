package com.usatayamish.expertcoursequizgame.load.data

interface LoadResult {

    fun isSuccessful(): Boolean

    fun message(): String

    data class Error(private val message: String) : LoadResult {

        override fun isSuccessful(): Boolean = false

        override fun message(): String = message

    }

    object Success : LoadResult {

        override fun isSuccessful(): Boolean = true

        override fun message(): String = throw IllegalStateException("cannot happen")
    }
}
