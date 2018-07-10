package com.publicmethod.practicebow

import android.arch.lifecycle.ViewModel
import android.util.Log
import com.publicmethod.practicebow.MVCViewModel.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.channels.BroadcastChannel
import kotlinx.coroutines.experimental.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.experimental.channels.SendChannel
import kotlinx.coroutines.experimental.channels.actor
import kotlinx.coroutines.experimental.launch

abstract class MVCViewModel<
        C : Command,
        A : Action,
        R : Result,
        S : State> : ViewModel() {

    interface Command
    interface Action
    interface State
    interface Result

    interface Interpreter<C, A> {
        fun interpret(command: C): A
    }

    interface Processor<A, R> {
        fun process(action: A): R
    }

    interface Reducer<R, S> {
        fun reduce(result: R): S
    }

    fun issueCommand(command: C) = launch(CommonPool) { commandChannel.send(command) }

    val stateChannel: BroadcastChannel<S> = ConflatedBroadcastChannel()

    abstract protected val interpreter: Interpreter<C, A>
    abstract protected val processor: Processor<A, R>
    abstract protected val reducer: Reducer<R, S>

    protected val commandChannel: SendChannel<C> = actor {
            for (command in channel) {
                Log.d("THING", command.toString())
                actionChannel.send(interpreter.interpret(command))
            }
        }

    protected val actionChannel: SendChannel<A> = actor {
            for (action in channel) {
                Log.d("THING", action.toString())
                resultChannel.send(processor.process(action))
            }
        }

    protected val resultChannel: SendChannel<R> = actor {
            for (result in channel) {
                Log.d("THING", result.toString())
                stateChannel.send(reducer.reduce(result))
            }
        }
}