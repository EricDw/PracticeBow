package com.publicmethod.practicebow.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.publicmethod.practicebow.threading.ContextProvider
import com.publicmethod.practicebow.ui.main.algebras.MainAction
import com.publicmethod.practicebow.ui.main.algebras.MainCommand
import com.publicmethod.practicebow.ui.main.algebras.MainResult
import com.publicmethod.practicebow.ui.main.algebras.MainState
import com.publicmethod.practicebow.Λrcher.ActionProcessor
import com.publicmethod.practicebow.Λrcher.CommandInterpreter
import com.publicmethod.practicebow.Λrcher.ModelViewCommand
import com.publicmethod.practicebow.Λrcher.StateReducer
import kotlinx.coroutines.experimental.CancellationException
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.actor
import kotlinx.coroutines.experimental.launch

class MainViewModel(private val contextProvider: ContextProvider = ContextProvider())
    : ViewModel(), ModelViewCommand<
        MainCommand,
        MainAction,
        MainResult,
        MainState,
        MainModel> {

    private val mutableStateLiveData = MutableLiveData<MainState>()

    val stateLiveData: LiveData<MainState>
        get() = mutableStateLiveData

    override val interpreter: CommandInterpreter<MainCommand, MainAction> = MainInterpreterReader
    override val processor: ActionProcessor<MainAction, MainResult> = MainProcessorReader
    override val reducer: StateReducer<MainResult, MainModel, MainState> = MainStateReducer

    private val parentJob = Job()

    private val interpreterRelay = actor<MainCommand>(
            context = contextProvider.BackgroundContext,
            capacity = Channel.UNLIMITED,
            parent = parentJob
    ) {
        for (command in channel) {
            interpreterActor.send(command)
        }
    }

    private val processorRelay = actor<MainAction>(
            context = contextProvider.BackgroundContext,
            capacity = Channel.UNLIMITED,
            parent = parentJob
    ) {
        for (action in channel) {
            processorActor.send(action)
        }
    }

    private val reducerRelay = actor<MainResult>(
            context = contextProvider.BackgroundContext,
            capacity = Channel.UNLIMITED,
            parent = parentJob
    ) {
        for (result in channel) {
            reducerActor.send(result)
        }
    }

    private val stateRelay = actor<MainState>(
            context = contextProvider.BackgroundContext,
            capacity = Channel.UNLIMITED,
            parent = parentJob
    ) {
        for (state in channel) {
            mutableStateLiveData.postValue(state)
        }
    }

    private val interpreterActor = actor<MainCommand>(
            context = contextProvider.BackgroundContext,
            capacity = Channel.UNLIMITED,
            parent = parentJob
    ) {
        for (command in channel) {
            processorRelay.send(interpreter.interpret(command))
        }
    }

    private val processorActor = actor<MainAction>(
            context = contextProvider.BackgroundContext,
            capacity = Channel.UNLIMITED,
            parent = parentJob
    ) {
        for (action in channel) {
            reducerRelay.send(processor.process(action))
        }
    }

    private val reducerActor = actor<MainResult>(
            context = contextProvider.BackgroundContext,
            capacity = Channel.UNLIMITED,
            parent = parentJob
    ) {
        for (result in channel) {
            stateRelay.send(reducer.reduce(result))
        }
    }

    override fun issueCommand(command: MainCommand) {
        launch(contextProvider.BackgroundContext) { interpreterRelay.send(command) }
    }

    override fun onCleared() {
        parentJob.cancel(CancellationException("ViewModel being destroyed"))
        super.onCleared()
    }
}