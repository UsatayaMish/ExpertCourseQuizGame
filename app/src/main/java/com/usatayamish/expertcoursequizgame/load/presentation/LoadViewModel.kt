package com.usatayamish.expertcoursequizgame.load.presentation


import com.usatayamish.expertcoursequizgame.ClearViewModel
import com.usatayamish.expertcoursequizgame.MyViewModel
import com.usatayamish.expertcoursequizgame.RunAsync
import com.usatayamish.expertcoursequizgame.load.data.LoadRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class LoadViewModel(
    private val repository: LoadRepository,
    private val observable: UiObservable,
    private val runAsync: RunAsync,
    private val clearViewModel: ClearViewModel
) : MyViewModel {

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

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

    fun startUpdates(observer: (LoadUiState) -> Unit) = observable.register(observer)


    fun stopUpdates() = observable.unregister()


}