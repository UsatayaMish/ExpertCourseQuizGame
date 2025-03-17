package com.usatayamish.expertcoursequizgame.load.presentation


import com.usatayamish.expertcoursequizgame.ClearViewModel
import com.usatayamish.expertcoursequizgame.core.MyViewModel
import com.usatayamish.expertcoursequizgame.core.RunAsync
import com.usatayamish.expertcoursequizgame.load.data.LoadRepository

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
                    val result = repository.load()
                    if (result.isSuccessful()) {
                        clearViewModel.clear(LoadViewModel::class.java)
                        LoadUiState.Success
                    } else
                        LoadUiState.Error(result.message())
                }) {
                observable.postUiState(it)
            }
        }
    }
}