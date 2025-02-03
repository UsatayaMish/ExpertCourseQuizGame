package com.usatayamish.expertcoursequizgame.load

import org.junit.Assert.assertEquals
import org.junit.Test

class LoadViewModelTest {

    @Test
    fun same_fragment() {
        val repository = FakeLoadRepository()
        repository.expectResult(LoadResult.Success)

        val observable = FakeUiObservable()
        val viewModel = LoadViewModel(
            repository = repository,
            observable = observable
        )
        val fragment = FakeFragment()

        viewModel.load(isFirstRun = true)//onViewCreated first time
        assertEquals(LoadUiState.Progress, observable.postUiStateCalledList.first())
        assertEquals(1, observable.postUiStateCalledList.size)

        assertEquals(1, repository.loadCalledCount)//ping repo to get data

        viewModel.startUpdates(observer = fragment)//onResume
        assertEquals(1, observable.registerCalledCount)

        assertEquals(LoadUiState.Progress, fragment.statesList.first())//give cached progress ui state to fragment
        assertEquals(1, fragment.statesList.size)

        repository.returnResult()//get data from server

        assertEquals(LoadUiState.Success, observable.postUiStateCalledList[1])
        assertEquals(2, observable.postUiStateCalledList.size)
        assertEquals(LoadUiState.Success, fragment.statesList[1])
        assertEquals(2, fragment.statesList.size)
    }

    @Test
    fun recreateActivity() {
        val repository = FakeLoadRepository()
        repository.expectResult(LoadResult.Error(message = "no internet"))

        val observable = FakeUiObservable()
        val viewModel = LoadViewModel(
            repository = repository,
            observable = observable
        )
        val fragment = FakeFragment()

        viewModel.load(isFirstRun = true)//onViewCreated first time
        assertEquals(LoadUiState.Progress, observable.postUiStateCalledList.first())
        assertEquals(1, observable.postUiStateCalledList.size)
        assertEquals(1, repository.loadCalledCount)//ping observable with progress and then repo

        viewModel.startUpdates(observer = fragment)//onResume
        assertEquals(1, observable.registerCalledCount)

        assertEquals(LoadUiState.Progress, fragment.statesList.first())
        assertEquals(1, fragment.statesList.size)

        viewModel.stopUpdates()//onPause and activity death
        assertEquals(1, observable.unregisterCalledCount)

        repository.returnResult()//provides error to observable
        assertEquals(1, fragment.statesList.size)
        assertEquals(LoadUiState.Error(message = "no internet"), observable.postUiStateCalledList[1])
        assertEquals(2, observable.postUiStateCalledList.size)

        val newInstanceOfFragment = FakeFragment()//new fragment instance after activity recreate

        viewModel.load(isFirstRun = false)//onViewCreated after activity recreate
        assertEquals(1, repository.loadCalledCount)
        assertEquals(2, observable.postUiStateCalledList.size)

        viewModel.startUpdates(observer = newInstanceOfFragment)//onResume after recreate
        assertEquals(2, observable.registerCalledCount)

        assertEquals(LoadUiState.Error(message = "no internet"), newInstanceOfFragment.statesList.first())
        assertEquals(1, newInstanceOfFragment.statesList.size)
    }
}

private class FakeFragment : (LoadUiState) -> Unit {

    val statesList = mutableListOf<LoadUiState>()

    override fun invoke(p1: LoadUiState) {
        statesList.add(p1)
    }
}

private class FakeLoadRepository : LoadRepository {

    private var loadResult: LoadResult? = null
    private var loadResultCallback: (LoadResult) -> Unit = {}

    fun expectResult(loadResult: LoadResult) {
        this.loadResult = loadResult
    }

    var loadCalledCount = 0

    override fun load(resultCallback: (loadResult) -> Unit) {
        loadCalledCount++
        loadResultCallback = resultCallback
    }

    fun returnResult() {
        loadResultCallback.invoke(loadResult)
    }
}

private class FakeUiObservable: UiObservable {

    private var uiStateCached : LoadUiState? = null
    private var observerCached: ((LoadUiState) -> Unit)? = null

    var registerCalledCount = 0

    override fun register(observer: (LoadUiState) -> Unit) {
        registerCalledCount++
        observerCached = observer
        if(uiStateCached != null) {
            observerCached!!.invoke(uiStateCached)
            uiStateCached = null
        }
    }

    var unregisterCalledCount = 0

    override fun unregister() {
        unregisterCalledCount++
        observerCached = null
    }

    val postUiStateCalledList = mutableListOf<LoadUiState>()

    override fun postUiState(uiState: LoadUiState) {
        postUiStateCalledList.add(uiState)
        if(observerCached == null) {
            uiStateCached = uiState
        } else {
            observerCached!!.invoke(uiState)
        }
    }
}