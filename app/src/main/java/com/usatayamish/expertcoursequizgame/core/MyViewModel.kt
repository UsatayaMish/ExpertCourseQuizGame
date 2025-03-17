package com.usatayamish.expertcoursequizgame.core

import com.usatayamish.expertcoursequizgame.load.presentation.UiObservable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

interface MyViewModel<T: Any> {

    interface Async<T: Any>: MyViewModel<T> {

        fun startUpdates(observer: (T) -> Unit)

        fun stopUpdates()
    }
    abstract class Abstract<T: Any>(
        protected val observable: UiObservable<T>
    ): Async<T> {

        protected val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

        override fun startUpdates(observer: (T) -> Unit) = observable.register(observer)

        override fun stopUpdates() = observable.unregister()
    }
}

