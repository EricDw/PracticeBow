package com.publicmethod.practicebow.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.publicmethod.practicebow.Archer.ActionProcessor
import com.publicmethod.practicebow.Archer.CommandInterpreter
import com.publicmethod.practicebow.Archer.StateReducer
import com.publicmethod.practicebow.LightBow
import com.publicmethod.practicebow.threading.ContextProvider
import com.publicmethod.practicebow.ui.main.algebras.MainAction
import com.publicmethod.practicebow.ui.main.algebras.MainCommand
import com.publicmethod.practicebow.ui.main.algebras.MainResult
import com.publicmethod.practicebow.ui.main.algebras.MainState
import kotlinx.coroutines.experimental.CancellationException

class MainViewModel(contextProvider: ContextProvider = ContextProvider())
    : LightBow<MainCommand, MainAction, MainResult, MainState, MainModel>(
        contextProvider.UIContext, contextProvider.BackgroundContext) {

    private val mutableStateLiveData = MutableLiveData<MainState>()

    val stateLiveData: LiveData<MainState>
        get() = mutableStateLiveData

    override val interpreter: CommandInterpreter<MainCommand, MainAction> =
            MainInterpreterReader

    override val processor: ActionProcessor<MainAction, MainResult> =
            MainProcessorReader

    override val reducer: StateReducer<MainResult, MainModel, MainState> =
            MainStateReducer

    override fun handleState(state: MainState) {
        mutableStateLiveData.value = state
    }

    override fun onCleared() {
        parentJob.cancel(CancellationException("ViewModel being destroyed"))
        super.onCleared()
    }
}