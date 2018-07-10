package com.publicmethod.practicebow.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.publicmethod.practicebow.MVCViewModel
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.launch

class MainViewModel
    : MVCViewModel<
        MainCommand,
        MainAction,
        MainResult,
        MainState>() {

    private val mutableStateLiveData = MutableLiveData<MainState>()

    val stateLiveData: LiveData<MainState>
        get() = mutableStateLiveData

    override val interpreter: Interpreter<MainCommand, MainAction>
        get() = MainInterpreter

    override val processor: Processor<MainAction, MainResult>
        get() = MainProcessor

    override val reducer: Reducer<MainResult, MainState>
        get() = MainReducer

    init {
        launch(CommonPool) {
            stateChannel.openSubscription().consumeEach {
                mutableStateLiveData.postValue(it)
            }
        }
    }

}