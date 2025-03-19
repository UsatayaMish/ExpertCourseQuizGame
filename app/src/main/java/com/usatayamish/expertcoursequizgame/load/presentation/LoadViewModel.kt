package com.usatayamish.expertcoursequizgame.load.presentation


import com.usatayamish.expertcoursequizgame.ClearViewModel
import com.usatayamish.expertcoursequizgame.R
import com.usatayamish.expertcoursequizgame.core.MyViewModel
import com.usatayamish.expertcoursequizgame.core.RunAsync
import com.usatayamish.expertcoursequizgame.load.data.BackendException
import com.usatayamish.expertcoursequizgame.load.data.LoadRepository
import com.usatayamish.expertcoursequizgame.load.data.NoInternetConnectionException

class LoadViewModel(
    private val repository: LoadRepository,
    observable: LoadUiObservable,
    private val runAsync: RunAsync,
    private val clearViewModel: ClearViewModel
) : MyViewModel.Abstract<LoadUiState>(observable) {

    fun load(isFirstRun: Boolean = true) {
        if (isFirstRun) {
            observable.postUiState(LoadUiState.Progress)
            runAsync.handleAsync(
                viewModelScope,
                {
                    try {
                        repository.load()
                        clearViewModel.clear(LoadViewModel::class.java)
                        LoadUiState.Success
                    } catch (e: Exception) {
                        when (e) {
                            is NoInternetConnectionException -> LoadUiState.ErrorRes()
                            is BackendException -> LoadUiState.Error(e.message?:"")
                            else -> LoadUiState.ErrorRes(R.string.sevice_unavailable)
                        }
                    }
                }) {
                observable.postUiState(it)
            }
        }
    }
}