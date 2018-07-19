package com.publicmethod.practicebow.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import arrow.effects.IO
import arrow.effects.async
import arrow.effects.fix
import com.publicmethod.practicebow.MVC.ActionProcessor
import com.publicmethod.practicebow.MVC.CommandInterpreter
import com.publicmethod.practicebow.MVC.ModelViewCommand
import com.publicmethod.practicebow.MVC.StateReducer

class MainViewModel
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

    override fun issueCommand(command: MainCommand) {
        command.threader.threadAsync(
                f = {
                        val action = interpreter.interpret(command)
                        val result = processor.process(action)
                        val state = reducer.reduce(result)
                        state
                },
                onError = {},
                onSuccess = { newState -> mutableStateLiveData.postValue(newState) },
                AC = IO.async()).fix().unsafeRunAsync {}
    }
}