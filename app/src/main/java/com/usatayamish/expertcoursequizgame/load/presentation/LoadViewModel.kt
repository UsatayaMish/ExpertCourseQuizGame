package com.usatayamish.expertcoursequizgame.load.presentation

import com.usatayamish.expertcoursequizgame.ClearViewModel
import com.usatayamish.expertcoursequizgame.R
import com.usatayamish.expertcoursequizgame.core.MyViewModel
import com.usatayamish.expertcoursequizgame.core.RunAsync
import com.usatayamish.expertcoursequizgame.load.data.BackendException
import com.usatayamish.expertcoursequizgame.load.data.LoadRepository
import com.usatayamish.expertcoursequizgame.load.data.NoInternetConnectionException

class LoadViewModel(
    private val now: Now,
    private val repository: LoadRepository,
    observable: LoadUiObservable,
    private val runAsync: RunAsync,
    private val clearViewModel: ClearViewModel
) : MyViewModel.Abstract<LoadUiState>(observable) {

    private val handleProcessDeath = HandleProcessDeath()

    fun load() {
        handleProcessDeath.firstProcessStarted()
        val timeStamp = now.timeInMillis()
        observable.postUiState(LoadUiState.Progress(timeStamp))
        loadInner(timeStamp)
    }

    fun loadInProgress(timeStamp: Long) {
            if (handleProcessDeath.processDeathHappened()) {
                handleProcessDeath.processDeathHandled()
                //ping backend one more time
                loadInner(timeStamp)
            } else {
                //activity config changes
            }

    }

    private fun loadInner(timeStamp: Long) {
        runAsync.handleAsync(
            viewModelScope,
            {
                try {
                    repository.load(timeStamp)
                    clearViewModel.clear(LoadViewModel::class.java)
                    LoadUiState.Success
                } catch (e: Exception) {
                    when (e) {
                        is NoInternetConnectionException -> LoadUiState.ErrorRes()
                        is BackendException -> LoadUiState.Error(e.message ?: "")
                        else -> LoadUiState.ErrorRes(R.string.sevice_unavailable)
                    }
                }
            }) {
            observable.postUiState(it)
        }
    }
}

interface Now {

    fun timeInMillis(): Long

    class Base : Now {

        override fun timeInMillis(): Long = System.currentTimeMillis()
    }
}