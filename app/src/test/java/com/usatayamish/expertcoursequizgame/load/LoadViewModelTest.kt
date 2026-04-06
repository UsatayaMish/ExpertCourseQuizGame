package com.usatayamish.expertcoursequizgame.load

import com.usatayamish.expertcoursequizgame.core.RunAsync
import com.usatayamish.expertcoursequizgame.game.FakeClearViewModel
import com.usatayamish.expertcoursequizgame.load.data.LoadRepository
import com.usatayamish.expertcoursequizgame.load.data.NoInternetConnectionException
import com.usatayamish.expertcoursequizgame.load.presentation.LoadUiObservable
import com.usatayamish.expertcoursequizgame.load.presentation.LoadUiState
import com.usatayamish.expertcoursequizgame.load.presentation.LoadViewModel
import com.usatayamish.expertcoursequizgame.load.presentation.Now
import com.usatayamish.expertcoursequizgame.load.presentation.UiObservable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LoadViewModelTest {

    private lateinit var repository: FakeLoadRepository
    private lateinit var observable: FakeLoadUiObservable
    private lateinit var runAsync: FakeRunAsync
    private lateinit var viewModel: LoadViewModel
    private lateinit var fragment: FakeFragment
    private lateinit var clearViewModel: FakeClearViewModel
    private lateinit var now: FakeNow

    @Before
    fun setup() {
        repository = FakeLoadRepository()
        observable = FakeLoadUiObservable.Base()
        runAsync = FakeRunAsync()
        clearViewModel = FakeClearViewModel()
        now = FakeNow()
        viewModel = LoadViewModel(
            repository = repository,
            observable = observable,
            runAsync = runAsync,
            clearViewModel = clearViewModel,
            now = now
        )
        fragment = FakeFragment()
    }

    @Test
    fun processDeath() {
        var state: LoadUiState = LoadUiState.Empty
        state.load(viewModel)
        assertEquals(LoadUiState.Progress(1), observable.postUiStateCalledList.first())
        assertEquals(1, observable.postUiStateCalledList.size)
        assertEquals(1, repository.loadCalledCount)
        assertEquals(1, repository.timeStamp)

        viewModel.startUpdates(observer = fragment)//onResume
        assertEquals(1, observable.registerCalledCount)

        state = fragment.statesList.last()

        assertEquals(1, fragment.statesList.size)
        assertEquals(LoadUiState.Progress(1), state)

        viewModel.stopUpdates()//onPause
        assertEquals(1, observable.unregisterCalledCount)

        //process death here
        viewModel = LoadViewModel(
            repository = repository,
            observable = observable,
            runAsync = runAsync,
            clearViewModel = clearViewModel,
            now = FakeNow()
        )

        state.load(viewModel)

        assertEquals(2, repository.loadCalledCount)
        assertEquals(1, repository.timeStamp)

        runAsync.returnResult()

        viewModel.startUpdates(fragment)
        assertEquals(2, observable.registerCalledCount)

        state = fragment.statesList.last()

        assertEquals(LoadUiState.Success, state)
    }

    @Test
    fun activityRecreated() {
        var state: LoadUiState = LoadUiState.Empty
        state.load(viewModel)
        assertEquals(LoadUiState.Progress(1), observable.postUiStateCalledList.first())
        assertEquals(1, observable.postUiStateCalledList.size)
        assertEquals(1, repository.loadCalledCount)
        assertEquals(1, repository.timeStamp)

        viewModel.startUpdates(observer = fragment)//onResume
        assertEquals(1, observable.registerCalledCount)

        state = fragment.statesList.last()

        assertEquals(1, fragment.statesList.size)
        assertEquals(LoadUiState.Progress(1), state)

        viewModel.stopUpdates()//onPause
        assertEquals(1, observable.unregisterCalledCount)

        //activity recreated

        state.load(viewModel)

        assertEquals(1, repository.loadCalledCount)
    }
}

//     @Test
//     fun same_fragment() {
//         repository.expectResult(LoadResult.Success)
//
//         viewModel.load(isFirstRun = true)//onViewCreated first time
//         assertEquals(LoadUiState.Progress, observable.postUiStateCalledList.first())
//
//         assertEquals(1, repository.loadCalledCount)//ping repo to get data
//
//         viewModel.startUpdates(observer = fragment)//onResume
//         assertEquals(1, observable.registerCalledCount)
//
//         assertEquals(
//             LoadUiState.Progress,
//             fragment.statesList.first()
//         )//give cached progress ui state to fragment
//         assertEquals(1, fragment.statesList.size)
//
//         runAsync.returnResult()
//         assertEquals(LoadUiState.Success, observable.postUiStateCalledList[1])
//         assertEquals(2, observable.postUiStateCalledList.size)
//         assertEquals(LoadUiState.Success, fragment.statesList[1])
//         assertEquals(2, fragment.statesList.size)
//         clearViewModel.assertClearCalled(LoadViewModel::class.java)
//     }
//
//     @Test
//     fun recreateActivity() {
//         repository.expectResult(LoadResult.Error(message = "no internet"))
//
//         viewModel.load(isFirstRun = true)//onViewCreated first time
//         assertEquals(LoadUiState.Progress, observable.postUiStateCalledList.first())
//         assertEquals(1, observable.postUiStateCalledList.size)
//         assertEquals(1, repository.loadCalledCount)//ping observable with progress and then repo
//
//         viewModel.startUpdates(observer = fragment)//onResume
//         assertEquals(1, observable.registerCalledCount)
//
//         assertEquals(LoadUiState.Progress, fragment.statesList.first())
//         assertEquals(1, fragment.statesList.size)
//
//         viewModel.stopUpdates()//onPause and activity death
//         assertEquals(1, observable.unregisterCalledCount)
//
//         runAsync.returnResult()
//         assertEquals(1, fragment.statesList.size)
//         assertEquals(
//             LoadUiState.Error(message = "no internet"),
//             observable.postUiStateCalledList[1]
//         )
//         assertEquals(2, observable.postUiStateCalledList.size)
//
//         val newInstanceOfFragment = FakeFragment()//new fragment instance after activity recreate
//
//         viewModel.load(isFirstRun = false)//onViewCreated after activity recreate
//         assertEquals(1, repository.loadCalledCount)
//         assertEquals(2, observable.postUiStateCalledList.size)
//
//         viewModel.startUpdates(observer = newInstanceOfFragment)//onResume after recreate
//         assertEquals(2, observable.registerCalledCount)
//
//         assertEquals(
//             LoadUiState.Error(message = "no internet"),
//             newInstanceOfFragment.statesList.first()
//         )
//         assertEquals(1, newInstanceOfFragment.statesList.size)
//     }
// }

private class FakeFragment : (LoadUiState) -> Unit {

    val statesList = mutableListOf<LoadUiState>()

    override fun invoke(p1: LoadUiState) {
        statesList.add(p1)
    }
}

private class FakeLoadRepository : LoadRepository {

    var loadCalledCount = 0
    var timeStamp = -1L
    private var success = true

    fun expectFailure() {
        success = false
    }

    override suspend fun load(timeStamp: Long) {
        this.timeStamp = timeStamp
        loadCalledCount++
        if(!success) throw NoInternetConnectionException()
    }
}

private interface FakeLoadUiObservable : FakeUiObservable<LoadUiState>, LoadUiObservable {

    class Base: FakeUiObservable.Abstract<LoadUiState>(), FakeLoadUiObservable
}

interface FakeUiObservable<T: Any> : UiObservable<T> {

    var registerCalledCount: Int
    var unregisterCalledCount: Int
    val postUiStateCalledList: MutableList<T>

    abstract class Abstract<T: Any> : FakeUiObservable<T> {

        private var uiStateCached: T? = null
        private var observerCached: ((T) -> Unit)? = null

        override var registerCalledCount: Int = 0
        override var unregisterCalledCount: Int = 0
        override val postUiStateCalledList: MutableList<T> = mutableListOf()

        override fun register(observer: (T) -> Unit) {
            registerCalledCount++
            observerCached = observer
            if (uiStateCached != null) {
                observerCached!!.invoke(uiStateCached!!)
                uiStateCached = null
            }
        }



        override fun unregister() {
            unregisterCalledCount++
            observerCached = null
        }



        override fun postUiState(uiState: T) {
            postUiStateCalledList.add(uiState)
            if (observerCached == null) {
                uiStateCached = uiState
            } else {
                observerCached!!.invoke(uiState)
            }
        }
    }
}



@Suppress("UNCHECKED_CAST")
class FakeRunAsync : RunAsync {

    private var result: Any? = null
    private var ui: (Any) -> Unit = {}

    override fun <T : Any> handleAsync(
        coroutineScope: CoroutineScope,
        heavyOperation: suspend () -> T,
        uiUpdate: (T) -> Unit
    ) = runBlocking {
        result = heavyOperation.invoke()
        ui = uiUpdate as (Any) -> Unit
    }

    fun returnResult() {
        ui.invoke(result!!)
    }
}

private class FakeNow(): Now {

    private var time: Long = 0L

    override fun timeInMillis(): Long {
        return ++time
    }


}